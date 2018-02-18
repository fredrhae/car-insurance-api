package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.model.enumerator.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class CustomerModifierCalculator {

    private static Map<Integer, Double> maleMap;
    private static Map<Integer, Double> femaleMap;

    @Autowired
    public CustomerModifierCalculator() {
        maleMap = new HashMap<>();
        femaleMap = new HashMap<>();

        populateMap(true,16, 24, 1.5);
        populateMap(true,25, 35, 1.2);
        populateMap(true,36, 60, 1.0);
        populateMap(true,61, 200, 1.3);
        populateMap(false,16, 24, 1.4);
        populateMap(false,25, 60, 1.0);
        populateMap(false,61, 200, 1.2);

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
}
