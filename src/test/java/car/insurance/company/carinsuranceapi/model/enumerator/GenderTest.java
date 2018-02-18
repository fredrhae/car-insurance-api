package car.insurance.company.carinsuranceapi.model.enumerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class GenderTest {

    @Test
    public void fromDescriptionMale() {
        assertEquals(Gender.MALE, Gender.fromDescription("male"));
        assertEquals(Gender.MALE, Gender.fromDescription("mAlE"));
        assertEquals(Gender.MALE, Gender.fromDescription("MALE"));
    }

    @Test
    public void fromDescriptionFemale() {
        assertEquals(Gender.FEMALE, Gender.fromDescription("female"));
        assertEquals(Gender.FEMALE, Gender.fromDescription("fEMalE"));
        assertEquals(Gender.FEMALE, Gender.fromDescription("FEMALE"));
    }

    @Test
    public void fromDescriptionNull() {
        assertNull(Gender.fromDescription(""));
        assertNull(Gender.fromDescription(null));
        assertNull(Gender.fromDescription("anything else"));
    }

    @Test
    public void getDescription() {
        assertEquals("Male", Gender.MALE.getDescription());
        assertEquals("Female", Gender.FEMALE.getDescription());
    }
}