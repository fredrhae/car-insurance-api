package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.BasePrice;
import car.insurance.company.carinsuranceapi.model.enumerator.VehicleType;
import car.insurance.company.carinsuranceapi.utils.EntitiesGeneratorHelper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BasePriceRepositoryTest {

    @Autowired
    private BasePriceRepository basePriceRepository;

    private BasePrice basePrice4Test;

    @Before
    public void setUp() throws Exception {
        IntStream.range(0,10)
                .forEach(i -> {
                    BasePrice basePriceSaved = basePriceRepository.save(EntitiesGeneratorHelper.generateBasePrice4Test());
                    if(i == 5){
                        basePrice4Test = basePriceSaved;
                    }
                });
    }

    @Test
    public void saveBasePriceTest(){
        BasePrice basePriceLoaded = basePriceRepository.findOne(basePrice4Test.getId());
        assertNotNull(basePriceLoaded);
        assertEquals(basePrice4Test, basePriceLoaded);
    }

    @Test
    public void updateBasePriceTest(){
        BasePrice basePriceLoaded = basePriceRepository.findOne(basePrice4Test.getId());
        String newModel = "New model";
        basePriceLoaded.setModel(newModel);
        basePriceRepository.save(basePriceLoaded);
        BasePrice basePriceUpdated = basePriceRepository.findOne(basePrice4Test.getId());
        assertNotNull(basePriceUpdated);
        assertEquals(newModel, basePriceUpdated.getModel());
    }

    @Test
    public void findAllTest(){
        List<BasePrice> basePriceList = basePriceRepository.findAll();
        assertThat(basePriceList.size(), Matchers.greaterThanOrEqualTo(10));
    }

    @Test
    public void deleteTest(){
        basePriceRepository.delete(basePrice4Test.getId());
        BasePrice basePriceLoaded = basePriceRepository.findOne(basePrice4Test.getId());
        assertNull(basePriceLoaded);
    }

    @Test
    public void deleteAllTest() {
        basePriceRepository.deleteAll();
        List<BasePrice> basePriceList = basePriceRepository.findAll();
        assertEquals(0, basePriceList.size());
    }

    @Test
    public void findByTypeAndYearAndMakeAndModelTest() {
        String make = "TEST FIND";
        String model = "TEST FIND MODEL";
        String year = "2011";
        basePriceRepository.save(
                EntitiesGeneratorHelper.generateBasePriceToFindTest(
                        VehicleType.MOTORCYCLE,
                        year,
                        model,
                        make));

        List<BasePrice> basePriceList = basePriceRepository.findByTypeAndYearAndMakeAndModelAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                year,
                make,
                model
        );

        assertNotNull(basePriceList);
        assertThat(basePriceList.size(), Matchers.greaterThanOrEqualTo(1));

        basePriceList = basePriceRepository.findByTypeAndYearAndMakeAndModelAllIgnoreCase(
                VehicleType.CAR,
                year,
                make,
                model);

        assertEquals(0, basePriceList.size());

        basePriceList = basePriceRepository.findByTypeAndYearAndMakeAndModelAllIgnoreCase(
                VehicleType.CAR,
                year,
                make,
                model);

        assertEquals(0, basePriceList.size());

        basePriceList = basePriceRepository.findByTypeAndYearAndMakeAndModelAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                "2012",
                make,
                model);

        assertEquals(0, basePriceList.size());

        basePriceList = basePriceRepository.findByTypeAndYearAndMakeAndModelAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                year,
                "other make",
                model);

        assertEquals(0, basePriceList.size());

        basePriceList = basePriceRepository.findByTypeAndYearAndMakeAndModelAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                year,
                make,
                "other model");

        assertEquals(0, basePriceList.size());
    }

    @Test
    public void findByTypeAndYearAndMakeTest() {
        String make = "TEST FIND";
        String year = "2011";
        basePriceRepository.save(
                EntitiesGeneratorHelper.generateBasePriceToFindTest(
                        VehicleType.MOTORCYCLE,
                        year,
                        make));

        List<BasePrice> basePriceList = basePriceRepository.findByTypeAndYearAndMakeAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                year,
                make);

        assertNotNull(basePriceList);
        assertThat(basePriceList.size(), Matchers.greaterThanOrEqualTo(1));

        basePriceList = basePriceRepository.findByTypeAndYearAndMakeAllIgnoreCase(
                VehicleType.TRUCK,
                year,
                make);

        assertEquals(0, basePriceList.size());

        basePriceList = basePriceRepository.findByTypeAndYearAndMakeAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                "1989",
                make);

        assertEquals(0, basePriceList.size());

        basePriceList = basePriceRepository.findByTypeAndYearAndMakeAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                year,
                "other make");

        assertEquals(0, basePriceList.size());
    }

    @Test
    public void findByTypeAndYearTest() {
        String year = "2011";
        basePriceRepository.save(
                EntitiesGeneratorHelper.generateBasePriceToFindTest(
                        VehicleType.MOTORCYCLE,
                        year));

        List<BasePrice> basePriceList = basePriceRepository.findByTypeAndYearAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                year);

        assertNotNull(basePriceList);
        assertThat(basePriceList.size(), Matchers.greaterThanOrEqualTo(1));

        basePriceList = basePriceRepository.findByTypeAndYearAllIgnoreCase(
                VehicleType.TRUCK,
                year);

        assertEquals(0, basePriceList.size());

        basePriceList = basePriceRepository.findByTypeAndYearAllIgnoreCase(
                VehicleType.MOTORCYCLE,
                "1989");

        assertEquals(0, basePriceList.size());
    }

    @Test
    public void findByTypeTest() {
        BasePrice basePrice = basePriceRepository.save(
                EntitiesGeneratorHelper.generateBasePriceToFindTest(
                        VehicleType.MOTORCYCLE));

        List<BasePrice> basePriceList = basePriceRepository.findByType(
                VehicleType.MOTORCYCLE);

        assertNotNull(basePriceList);
        assertThat(basePriceList.size(), Matchers.greaterThanOrEqualTo(1));
    }
}