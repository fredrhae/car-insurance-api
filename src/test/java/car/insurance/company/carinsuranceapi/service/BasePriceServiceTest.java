package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.model.BasePrice;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import car.insurance.company.carinsuranceapi.model.enumerator.VehicleType;
import car.insurance.company.carinsuranceapi.repository.BasePriceRepository;
import car.insurance.company.carinsuranceapi.utils.EntitiesGeneratorHelper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BasePriceServiceTest {

    @Autowired
    BasePriceRepository basePriceRepository;

    private BasePriceService basePriceService;

    private BasePrice vehicleAllParameters;
    private BasePrice vehicleWithoutModel;
    private BasePrice vehicleWithTypeAndYear;
    private BasePrice vehicleWithType;

    @Before
    public void setUp(){
        basePriceRepository.deleteAll();
        basePriceService = new BasePriceService(basePriceRepository);
        IntStream.range(0,10)
                .forEach(i -> {
                    basePriceRepository.save(EntitiesGeneratorHelper.generateBasePrice4Test());
                });

        vehicleAllParameters = saveBasePrice(VehicleType.CAR,"2010",
                "TEST ALL PARAMETERS", "TEST ALL PARAMETERS MODEL");

        vehicleWithoutModel = saveBasePrice(VehicleType.CAR,"2011",
                "TEST WITHOUT MODEL", null);

        vehicleWithTypeAndYear = saveBasePrice(VehicleType.CAR,"2012",
                null, null);

        vehicleWithType = saveBasePrice(VehicleType.TRUCK,null,
                null, null);
    }

    //US04
    @Test
    public void retrieveBasePriceAllParametersTest() {
        Vehicle vehicleToTest = Vehicle.builder()
                                    .type(vehicleAllParameters.getType())
                                    .manufacturingYear(vehicleAllParameters.getYear())
                                    .make(vehicleAllParameters.getMake())
                                    .model(vehicleAllParameters.getModel())
                                    .build();

        Double basePrice = basePriceService.retrieveBasePrice(vehicleToTest);
        assertNotNull(basePrice);
        assertThat(basePrice, Matchers.greaterThan(1000.0));
    }

    //US04
    @Test
    public void retrieveBasePriceWithoutModelTest() {
        Vehicle vehicleToTest = Vehicle.builder()
                .type(vehicleWithoutModel.getType())
                .manufacturingYear(vehicleWithoutModel.getYear())
                .make(vehicleWithoutModel.getMake())
                .build();

        Double basePrice = basePriceService.retrieveBasePrice(vehicleToTest);
        assertNotNull(basePrice);
        assertThat(basePrice, Matchers.greaterThan(1000.0));
    }

    //US04
    @Test
    public void retrieveBasePriceWithTypeAndYearTest() {
        Vehicle vehicleToTest = Vehicle.builder()
                .type(vehicleWithTypeAndYear.getType())
                .manufacturingYear(vehicleWithTypeAndYear.getYear())
                .build();

        Double basePrice = basePriceService.retrieveBasePrice(vehicleToTest);
        assertNotNull(basePrice);
        assertThat(basePrice, Matchers.greaterThan(1000.0));
    }

    //US04
    @Test
    public void retrieveBasePriceOnlyTypeTest() {
        Vehicle vehicleToTest = Vehicle.builder()
                .type(vehicleWithType.getType())
                .build();

        Double basePrice = basePriceService.retrieveBasePrice(vehicleToTest);
        assertNotNull(basePrice);
        assertThat(basePrice, Matchers.greaterThan(1000.0));
    }

    //US04
    @Test
    public void retrieveBasePriceNoneParameterTest() {
        Vehicle vehicleToTest = Vehicle.builder()
                .build();

        Double basePrice = basePriceService.retrieveBasePrice(vehicleToTest);
        assertNotNull(basePrice);
        assertEquals(1000.0, basePrice);
    }

    private BasePrice saveBasePrice(VehicleType type, String year, String make, String model){
        return basePriceRepository.save(
                EntitiesGeneratorHelper.generateBasePriceToFindTest(
                        type,
                        year,
                        model,
                        make));
    }

}