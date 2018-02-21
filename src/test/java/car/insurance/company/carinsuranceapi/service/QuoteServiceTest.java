package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.model.Quote;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import car.insurance.company.carinsuranceapi.model.enumerator.QuoteStatus;
import car.insurance.company.carinsuranceapi.repository.BasePriceRepository;
import car.insurance.company.carinsuranceapi.repository.QuoteRepository;
import car.insurance.company.carinsuranceapi.utils.EntitiesGeneratorHelper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuoteServiceTest {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private BasePriceRepository basePriceRepository;

    private BasePriceService basePriceService;

    private QuoteService quoteService;

    private CustomerModifierService customerModifierService;

    private Customer customer4Test;

    private Vehicle vehicle4Test;

    @Before
    public void setUp() throws Exception {
        basePriceService = new BasePriceService(basePriceRepository);
        customerModifierService = new CustomerModifierService();
        quoteService = new QuoteService(quoteRepository, basePriceService, customerModifierService);
        customer4Test = EntitiesGeneratorHelper.generateCustomer4Test();
        vehicle4Test = EntitiesGeneratorHelper.generateVehicle4Test();
    }

    @Test
    public void generateQuote() {
        quoteRepository.deleteAll();



        // Assure that there is no quote saved
        assertEquals(0, quoteRepository.findAll().size());

        // Generate quote and assert that was saved on database
        Quote quote4Test = quoteService.generateQuote(customer4Test,vehicle4Test);

        assertNotNull(quote4Test);
        assertThat(quoteRepository.findAll().size(), Matchers.greaterThan(0));
        assertEquals(customer4Test, quote4Test.getCustomer());
        assertEquals(vehicle4Test, quote4Test.getVehicle());
        assertNotNull(quote4Test.getNumber());
    }

    @Test
    public void processQuote() throws InterruptedException, ExecutionException {
        Quote quote4Test = quoteService.generateQuote(customer4Test,vehicle4Test);

        assertNotNull(quote4Test);
        assertEquals(QuoteStatus.OPEN, quote4Test.getStatus());
        CompletableFuture<Quote> alreadyCompleted = quoteService.processQuote(quote4Test);

        assertEquals(QuoteStatus.DONE, alreadyCompleted.get().getStatus());
        assertNotNull(alreadyCompleted.get().getPrice());
        assertThat(alreadyCompleted.get().getPrice(), Matchers.greaterThanOrEqualTo(1000.00));
    }
}