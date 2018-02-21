package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.repository.CustomerRepository;
import car.insurance.company.carinsuranceapi.utils.EntitiesGeneratorHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp(){
        customerService = new CustomerService(customerRepository);
    }

    @Test
    public void updateCostumerIfAlreadyExists() {
        Customer currentCustomer = EntitiesGeneratorHelper.generateCustomer4Test();
        currentCustomer = customerService.saveCustomer(currentCustomer);

        // Save locally the initial values from customer
        Long id = currentCustomer.getId();
        String ssn = currentCustomer.getSsn();
        String name = currentCustomer.getName();
        String address = currentCustomer.getAddress();
        String email = currentCustomer.getEmail();
        String phoneNumber = currentCustomer.getPhoneNumber();

        // Change content from customer
        String anything = "anything";

        currentCustomer.setName(name + anything);
        currentCustomer.setEmail(email + anything);
        currentCustomer.setPhoneNumber(phoneNumber + anything);
        currentCustomer.setAddress(address + anything);

        customerService.updateCostumerIfAlreadyExists(currentCustomer);

        Customer loadedCustomer = customerService.findCustomerBySsn(currentCustomer.getSsn());

        assertNotNull(loadedCustomer);

        //Assure the initial value is not anymore on customer
        assertNotEquals(name, loadedCustomer.getName());
        assertNotEquals(address, loadedCustomer.getAddress());
        assertNotEquals(email, loadedCustomer.getEmail());
        assertNotEquals(phoneNumber, loadedCustomer.getPhoneNumber());

        //Assure that is the same customer and id
        assertEquals(id, loadedCustomer.getId());
        assertEquals(ssn, loadedCustomer.getSsn());
    }

    @Test
    public void assureNothingIsDoneIfCustomerNotExists(){
        customerRepository.deleteAll();

        assertEquals(0, customerRepository.findAll().size());

        Customer currentCustomer = EntitiesGeneratorHelper.generateCustomer4Test();

        customerService.updateCostumerIfAlreadyExists(currentCustomer);

        assertEquals(0, customerRepository.findAll().size());
    }
}