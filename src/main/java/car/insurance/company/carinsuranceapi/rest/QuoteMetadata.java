package car.insurance.company.carinsuranceapi.rest;

import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteMetadata {

    private Customer customer;

    private Vehicle vehicle;
}
