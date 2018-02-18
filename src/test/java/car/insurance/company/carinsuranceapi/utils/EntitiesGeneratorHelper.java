package car.insurance.company.carinsuranceapi.utils;

import car.insurance.company.carinsuranceapi.model.BasePrice;
import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.model.Quote;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import car.insurance.company.carinsuranceapi.model.enumerator.Gender;
import car.insurance.company.carinsuranceapi.model.enumerator.QuoteStatus;
import car.insurance.company.carinsuranceapi.model.enumerator.VehicleType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class EntitiesGeneratorHelper {

    public static Customer generateCustomer4Test(){
        Random newRandom = new Random();
        return Customer.builder()
                .birthDate(LocalDateTime.now())
                .address("Fools's street " + newRandom.nextInt(2000))
                .email("teste@email.com")
                .gender((newRandom.nextInt()%2 == 0) ? Gender.MALE : Gender.FEMALE)
                .phoneNumber("+1 123 4 567" + newRandom.nextInt(9))
                .name("Great teste r" + newRandom.nextInt())
                .ssn(newRandom.nextLong())
                .build();
    }

    public static BasePrice generateBasePrice4Test(){
        Random newRandom = new Random();
        return BasePrice.builder()
                .year(LocalDate.of(2000 + newRandom.nextInt(18),10,10))
                .type(VehicleType.CAR)
                .incidentAvgYear(1 + newRandom.nextDouble())
                .model("Any model")
                .make("Any make")
                .basePrice(1000.0 + newRandom.nextInt(1000))
                .build();
    }

    public static Vehicle generateVehicle4Test(){
        Random newRandom = new Random();
        return Vehicle.builder()
                .type(VehicleType.TRUCK)
                .model("Any model")
                .make("Any make")
                .build();
    }

    public static Quote generateQuote4Test(){
        Random newRandom = new Random();
        return Quote.builder()
                .customer(generateCustomer4Test())
                .vehicle(generateVehicle4Test())
                .price(newRandom.nextDouble())
                .status(QuoteStatus.DONE)
                .build();
    }
}
