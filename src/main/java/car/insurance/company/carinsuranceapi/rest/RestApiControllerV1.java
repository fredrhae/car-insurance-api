package car.insurance.company.carinsuranceapi.rest;

import car.insurance.company.carinsuranceapi.exception.CompanyInsuranceApiException;
import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.model.Quote;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import car.insurance.company.carinsuranceapi.service.CustomerService;
import car.insurance.company.carinsuranceapi.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
public class RestApiControllerV1 {
    private static final Logger logger = LoggerFactory.getLogger(RestApiControllerV1.class);
    private static final String LOG_HEADER = "[REST API]";

    private final CustomerService customerService;
    private final QuoteService quoteService;

    @Autowired
    public RestApiControllerV1(final CustomerService customerService,
                               final QuoteService quoteService) {
        this.customerService = customerService;
        this.quoteService = quoteService;

    }

    @Async
    @RequestMapping(method = RequestMethod.POST, value = "/quote",  consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<ResponseEntity<Long>> requestQuoting(@RequestBody QuoteMetadata quoteMetadata)
    {
        logger.info("{} Initializing quoting process for the following metada: {}", LOG_HEADER, quoteMetadata.toString());

        ResponseEntity responseRequest = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        Customer customerFromQuote = quoteMetadata.getCustomer();
        Vehicle vehicleFromQuote = quoteMetadata.getVehicle();
        try {

            customerService.updateCostumerIfAlreadyExists(customerFromQuote);

            Quote quote = quoteService.generateQuote(customerFromQuote, vehicleFromQuote);

            responseRequest = new ResponseEntity(quote.getId(), HttpStatus.OK);

            quoteService.processQuote(quote);

        } catch (InterruptedException e) {
            logger.error("{} Error on quote processing: {}", LOG_HEADER, e.getMessage());
        } catch (CompanyInsuranceApiException e) {
            logger.error("{} Error on saving entities customer/vehicle, check data is ok and try again.",
                            LOG_HEADER, e.getMessage());
        }

        return CompletableFuture.completedFuture(responseRequest);
    }



    @CrossOrigin
    @GetMapping(value = "/quote/status", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
    public CompletableFuture<ResponseEntity<Quote>> quoteStatus()
    {
        return null;
    }

    @CrossOrigin
    @GetMapping(value = "/quote/information", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
    public CompletableFuture<ResponseEntity<Quote>> quoteInformation()
    {
        return null;
    }
}
