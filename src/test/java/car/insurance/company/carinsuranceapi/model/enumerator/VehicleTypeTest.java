package car.insurance.company.carinsuranceapi.model.enumerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class VehicleTypeTest {

    @Test
    public void fromDescriptionCar() {
        assertEquals(VehicleType.CAR, VehicleType.fromDescription("car"));
        assertEquals(VehicleType.CAR, VehicleType.fromDescription("CAr"));
        assertEquals(VehicleType.CAR, VehicleType.fromDescription("CaR"));
        assertEquals(VehicleType.CAR, VehicleType.fromDescription("CAR"));
    }

    @Test
    public void fromDescriptionMotorcycle() {
        assertEquals(VehicleType.MOTORCYCLE, VehicleType.fromDescription("motorcycle"));
        assertEquals(VehicleType.MOTORCYCLE, VehicleType.fromDescription("motORCyClE"));
        assertEquals(VehicleType.MOTORCYCLE, VehicleType.fromDescription("MOTORCYCLE"));
    }

    @Test
    public void fromDescriptionTruck() {
        assertEquals(VehicleType.TRUCK, VehicleType.fromDescription("truck"));
        assertEquals(VehicleType.TRUCK, VehicleType.fromDescription("trUCk"));
        assertEquals(VehicleType.TRUCK, VehicleType.fromDescription("TRUCK"));
    }

    @Test
    public void fromDescriptionOther() {
        assertEquals(VehicleType.OTHER, VehicleType.fromDescription("other"));
        assertEquals(VehicleType.OTHER, VehicleType.fromDescription("otHEr"));
        assertEquals(VehicleType.OTHER, VehicleType.fromDescription("OTHER"));
    }

    @Test
    public void fromDescriptionNull() {
        assertNull(VehicleType.fromDescription(""));
        assertNull(VehicleType.fromDescription(null));
        assertNull(VehicleType.fromDescription("anything else"));
    }

    @Test
    public void getDescription() {
        assertEquals("car", VehicleType.CAR.getDescription());
        assertEquals("motorcycle", VehicleType.MOTORCYCLE.getDescription());
        assertEquals("truck", VehicleType.TRUCK.getDescription());
        assertEquals("other", VehicleType.OTHER.getDescription());
    }
}