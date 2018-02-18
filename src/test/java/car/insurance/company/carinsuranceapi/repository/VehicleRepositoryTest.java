package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.Vehicle;
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
public class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    private Vehicle vehicle4Test;

    @Before
    public void setUp() throws Exception {
        IntStream.range(0,10)
                .forEach(i -> {
                    Vehicle vehicleSaved = vehicleRepository.save(EntitiesGeneratorHelper.generateVehicle4Test());
                    if(i == 5){
                        vehicle4Test = vehicleSaved;
                    }
                });

    }

    @Test
    public void saveVehicleTest(){
        Vehicle vehicleLoaded = vehicleRepository.findOne(vehicle4Test.getId());
        assertNotNull(vehicleLoaded);
        assertEquals(vehicle4Test, vehicleLoaded);
    }

    @Test
    public void updateVehicleTest(){
        Vehicle vehicleLoaded = vehicleRepository.findOne(vehicle4Test.getId());
        String newModel = "New model";
        vehicleLoaded.setModel(newModel);
        vehicleRepository.save(vehicleLoaded);
        Vehicle vehicleUpdated = vehicleRepository.findOne(vehicle4Test.getId());
        assertNotNull(vehicleUpdated);
        assertEquals(newModel, vehicleUpdated.getModel());
    }

    @Test
    public void findAllTest(){
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList.size(), Matchers.greaterThanOrEqualTo(10));
    }

    @Test
    public void deleteTest(){
        vehicleRepository.delete(vehicle4Test.getId());
        Vehicle vehicleLoaded = vehicleRepository.findOne(vehicle4Test.getId());
        assertNull(vehicleLoaded);
    }

    @Test
    public void deleteAllTest() {
        vehicleRepository.deleteAll();
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertEquals(0, vehicleList.size());
    }
}