package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.exception.CompanyInsuranceApiException;
import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private static final String LOG_HEADER = "[CUSTOMER SERV.]";

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerBySsn(String ssn) {
        return customerRepository.findBySsn(ssn);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void updateCostumerIfAlreadyExists(Customer customerFromQuote) throws CompanyInsuranceApiException {
        Customer customerLoaded = customerRepository.findBySsn(customerFromQuote.getSsn());
        if(customerLoaded != null) {
            Customer customerToUpdate = Customer.builder()
                    .id(customerLoaded.getId())
                    .ssn(customerLoaded.getSsn())
                    .name(customerFromQuote.getName())
                    .email(customerFromQuote.getEmail())
                    .birthDate(customerFromQuote.getBirthDate())
                    .address(customerFromQuote.getAddress())
                    .phoneNumber(customerFromQuote.getPhoneNumber())
                    .build();

            customerFromQuote = customerRepository.save(customerToUpdate);
        }
    }
}
