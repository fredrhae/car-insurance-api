package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.model.enumerator.Gender;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class CustomerModifierService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerModifierService.class);
    private static final String LOG_HEADER = "[MODIFIER CALC.]";

    private static Map<Integer, Double> maleMap;
    private static Map<Integer, Double> femaleMap;

    @Autowired
    public CustomerModifierService() {
        maleMap = new HashMap<>();
        femaleMap = new HashMap<>();

        populateMap(true,16, 25, 1.5);
        populateMap(true,25, 36, 1.2);
        populateMap(true,36, 61, 1.0);
        populateMap(true,61, 201, 1.3);
        populateMap(false,16, 25, 1.4);
        populateMap(false,25, 61, 1.0);
        populateMap(false,61, 201, 1.2);
    }

    private void populateMap(Boolean isMaleMap, Integer initialAge, Integer finalAge, Double modifier) {
        IntStream.range(initialAge,finalAge).forEach(
                age -> {
                    if(isMaleMap) {
                        maleMap.put(age, modifier);
                    } else {
                        femaleMap.put(age, modifier);
                    }
                }
        );
    }

    public Double calculateModifier(Gender gender, Integer age){
        Double modifier = null;
        if(Gender.MALE.equals(gender)) {
            modifier = maleMap.get(age);
        } else {
            modifier = femaleMap.get(age);
        }

        return modifier;
    }

    public Double calculateModifier(Gender gender, Date birthDate){
        Integer age = calculateAge(birthDate);
        Double modifier = null;
        if(Gender.MALE.equals(gender)) {
            modifier = maleMap.get(age);
        } else {
            modifier = femaleMap.get(age);
        }

        return modifier;
    }

    public Integer calculateAge(Date birthDate) {
        DateTime now = DateTime.now();
        DateTime birthDateJoda = new DateTime(birthDate);
        Period agePeriod = new Period(birthDateJoda, now);
        return agePeriod.getYears();
    }
}
