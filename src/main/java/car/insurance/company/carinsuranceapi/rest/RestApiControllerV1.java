package car.insurance.company.carinsuranceapi.rest;

import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.model.Quote;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/")
public class RestApiControllerV1 {
    private static final Logger logger = LoggerFactory.getLogger(RestApiControllerV1.class);

    @CrossOrigin
    @PostMapping(value = "/quote", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<ResponseEntity<Long>> requestQuoting(
                                    @RequestBody Customer customer,
                                    @RequestBody Vehicle vehicle)
    {

        return null;
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
