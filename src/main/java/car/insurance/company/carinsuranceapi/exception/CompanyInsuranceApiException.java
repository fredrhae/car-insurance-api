package car.insurance.company.carinsuranceapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CompanyInsuranceApiException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(CompanyInsuranceApiException.class);

    private static final long serialVersionUID = -4933909063053829978L;

    public CompanyInsuranceApiException(String message) {
        super("Some error ocurred during the quote processing, reason: '" + message);
        logger.error("Some error ocurred in quote processing, reason: '" + message);
    }
}
