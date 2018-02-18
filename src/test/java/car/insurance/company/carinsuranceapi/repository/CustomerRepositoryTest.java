package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.Customer;
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
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer4Test;

    @Before
    public void setUp() throws Exception {
        IntStream.range(0,10)
                .forEach(i -> {
                    Customer customerSaved = customerRepository.save(EntitiesGeneratorHelper.generateCustomer4Test());
                    if(i == 5){
                        customer4Test = customerSaved;
                    }
                });

    }

    @Test
    public void saveCustomerTest(){
        Customer customerLoaded = customerRepository.findOne(customer4Test.getId());
        assertNotNull(customerLoaded);
        assertEquals(customer4Test, customerLoaded);
    }

    @Test
    public void updateCustomerTest(){
        Customer customerLoaded = customerRepository.findOne(customer4Test.getId());
        String newEmail = "new.email@test.com";
        customerLoaded.setEmail(newEmail);
        customerRepository.save(customerLoaded);
        Customer customerUpdated = customerRepository.findOne(customer4Test.getId());
        assertNotNull(customerUpdated);
        assertEquals(newEmail, customerUpdated.getEmail());
    }

    @Test
    public void findBySsn() {
        Customer customerLoaded = customerRepository.findOne(customer4Test.getId());
        assertNotNull(customerLoaded);
        assertEquals(customer4Test, customerLoaded);
    }

    @Test
    public void findAllTest(){
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList.size(), Matchers.greaterThanOrEqualTo(10));
    }

    @Test
    public void deleteTest(){
        customerRepository.delete(customer4Test.getId());
        Customer customerLoaded = customerRepository.findOne(customer4Test.getId());
        assertNull(customerLoaded);
    }

    @Test
    public void deleteAllTest() {
        customerRepository.deleteAll();
        List<Customer> customerList = customerRepository.findAll();
        assertEquals(0, customerList.size());
    }
}