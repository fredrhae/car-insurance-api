package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.model.enumerator.Gender;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class CustomerModifierServiceTest {

    private CustomerModifierService customerModifierService;

    @Before
    public void setUp(){
        customerModifierService = new CustomerModifierService();
    }

    //US03 - Modifier 1.5
    @Test
    public void calculateModifierYoungDriverMaleTest() {
        assertModifierInRange(16,24,Gender.MALE, 1.5);
        assertModifierInRangeWithDate(1994,2002,Gender.MALE, 1.5);

    }

    //US03 - Modifier 1.2
    @Test
    public void calculateModifierMatureDriverMaleTest() {
        assertModifierInRange(25,35,Gender.MALE, 1.2);
        assertModifierInRangeWithDate(1983,1993,Gender.MALE, 1.2);

    }

    //US03 - Modifier 1
    @Test
    public void calculateModifierOldDriverMaleTest() {
        assertModifierInRange(36,60,Gender.MALE, 1.0);
        assertModifierInRangeWithDate(1958,1982,Gender.MALE, 1.0);
    }

    //US03 - Modifier 1.3
    @Test
    public void calculateModifierOlderDriverMaleTest() {
        assertModifierInRange(61,100,Gender.MALE, 1.3);
        assertModifierInRangeWithDate(1918,1957,Gender.MALE, 1.3);
    }

    //US03 - Modifier 1.4
    @Test
    public void calculateModifierYoungDriverFemaleTest() {
        assertModifierInRange(16,24,Gender.FEMALE, 1.4);
        assertModifierInRangeWithDate(1994,2002,Gender.FEMALE, 1.4);
    }

    //US03 - Modifier 1
    @Test
    public void calculateModifierMatureDriverFemaleTest() {
        assertModifierInRange(25,60,Gender.FEMALE, 1.0);
        assertModifierInRangeWithDate(1958,1993,Gender.FEMALE, 1.0);
    }

    //US03 - Modifier 1.2
    @Test
    public void calculateModifierOlderDriverFemaleTest() {
        assertModifierInRange(61,100,Gender.FEMALE, 1.2);
        assertModifierInRangeWithDate(1918,1957,Gender.FEMALE, 1.2);
    }

    private void assertModifierInRange(Integer initialAge, Integer finalAge, Gender gender,Double expectedModifier) {
        IntStream.range(initialAge,finalAge).
                forEach(
                        i -> assertEquals(expectedModifier, customerModifierService.calculateModifier(gender,i))
                );
    }

    private void assertModifierInRangeWithDate(Integer initialYear, Integer finalYear, Gender gender,Double expectedModifier) {
        IntStream.range(initialYear, finalYear).
                forEach(
                        i -> assertEquals(expectedModifier, customerModifierService.calculateModifier(gender,
                                new DateTime(i,1,1,1,1).toDate()))
                );
    }
}