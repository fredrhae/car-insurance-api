package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.exception.CompanyInsuranceApiException;
import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.model.Quote;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import car.insurance.company.carinsuranceapi.model.enumerator.QuoteStatus;
import car.insurance.company.carinsuranceapi.repository.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
    private static final String LOG_HEADER = "[QUOTE SERV.]";

    private final QuoteRepository quoteRepository;
    private final BasePriceService basePriceService;
    private final CustomerModifierService customerModifierService;

    @Autowired
    public QuoteService(final QuoteRepository quoteRepository,
                        final BasePriceService basePriceService,
                        final CustomerModifierService customerModifierService){
        this.quoteRepository = quoteRepository;
        this.basePriceService = basePriceService;
        this.customerModifierService = customerModifierService;
    }

    public Quote saveQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    public Quote generateQuote(Customer customer, Vehicle vehicle) throws CompanyInsuranceApiException{
        logger.info("{} Saving quoting for customer {}...", LOG_HEADER, customer.getName());
        Quote quoteToSave = Quote.builder()
                                .customer(customer)
                                .vehicle(vehicle)
                                .build();
        return quoteRepository.save(quoteToSave);
    }

    public Quote findQuoteByNumber(Long number) {
        return quoteRepository.findOne(number);
    }


    @Async
    public CompletableFuture<Quote> processQuote(Quote quoteToProcess) throws InterruptedException {
        logger.info("{} Processing quoting of number {}...", LOG_HEADER, quoteToProcess.getId());

        Double basePrice = basePriceService.retrieveBasePrice(quoteToProcess.getVehicle());
        logger.info("{} The base price found was {}...", LOG_HEADER, basePrice);

        Double customerModifier = customerModifierService.calculateModifier(
                            quoteToProcess.getCustomer().getGender(),
                            quoteToProcess.getCustomer().getBirthDate());
        logger.info("{} The modifier found was {}...", LOG_HEADER, customerModifier);

        Thread.sleep(10000L);

        logger.info("{} Finalizing the quote processing...",LOG_HEADER);
        quoteToProcess.setPrice(basePrice*customerModifier);
        quoteToProcess.setStatus(QuoteStatus.DONE);
        return CompletableFuture.completedFuture(quoteRepository.save(quoteToProcess));
    }
}
