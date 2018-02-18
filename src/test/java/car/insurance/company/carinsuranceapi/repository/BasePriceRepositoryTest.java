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
}