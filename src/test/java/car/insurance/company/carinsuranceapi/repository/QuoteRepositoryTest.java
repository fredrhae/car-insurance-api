package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.Quote;
import car.insurance.company.carinsuranceapi.utils.EntitiesGeneratorHelper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuoteRepositoryTest {
    @Autowired
    private QuoteRepository quoteRepository;

    private Quote quote4Test;

    @Before
    public void setUp() throws Exception {
        IntStream.range(0,10)
                .forEach(i -> {
                    Quote quoteSaved = quoteRepository.save(EntitiesGeneratorHelper.generateQuote4Test());
                    if(i == 5){
                        quote4Test = quoteSaved;
                    }
                });

    }

    @Test
    public void saveQuoteTest(){
        Quote customerLoaded = quoteRepository.findOne(quote4Test.getId());
        assertNotNull(customerLoaded);
        assertEquals(quote4Test, customerLoaded);
    }

    @Test
    public void updateQuoteTest(){
        Quote quoteLoaded = quoteRepository.findOne(quote4Test.getId());
        Double newPrice = 1000.0;
        quoteLoaded.setPrice(newPrice);
        quoteRepository.save(quoteLoaded);
        Quote quoteUpdated = quoteRepository.findOne(quote4Test.getId());
        assertNotNull(quoteUpdated);
        assertEquals(newPrice, quoteUpdated.getPrice());
    }

    @Test
    public void findAllTest(){
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList.size(), Matchers.greaterThanOrEqualTo(10));
    }

    @Test
    public void deleteTest(){
        quoteRepository.delete(quote4Test.getId());
        Quote quoteLoaded = quoteRepository.findOne(quote4Test.getId());
        assertNull(quoteLoaded);
    }

    @Test
    public void deleteAllTest() {
        quoteRepository.deleteAll();
        List<Quote> quoteList = quoteRepository.findAll();
        assertEquals(0, quoteList.size());
    }
}