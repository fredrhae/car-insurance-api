package car.insurance.company.carinsuranceapi.utils;

import car.insurance.company.carinsuranceapi.model.BasePrice;
import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.model.Quote;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import car.insurance.company.carinsuranceapi.model.enumerator.Gender;
import car.insurance.company.carinsuranceapi.model.enumerator.QuoteStatus;
import car.insurance.company.carinsuranceapi.model.enumerator.VehicleType;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

public class EntitiesGeneratorHelper {

    public static Customer generateCustomer4Test(){
        Random newRandom = new Random();
        Date birthDate = new DateTime(1990, 1, 1, 1, 1, 1).toDate();
        return Customer.builder()
                .birthDate(birthDate)
                .address("Fools's street " + newRandom.nextInt(2000))
                .email("teste@email.com")
                .gender((newRandom.nextInt()%2 == 0) ? Gender.MALE : Gender.FEMALE)
                .phoneNumber("+1 123 4 567" + newRandom.nextInt(9))
                .name("Great teste r" + newRandom.nextInt())
                .ssn("12" + newRandom.nextLong())
                .build();
    }

    public static BasePrice generateBasePrice4Test(){
        Random newRandom = new Random();
        return BasePrice.builder()
                .year(String.valueOf(2000 + newRandom.nextInt(18)))
                .type(VehicleType.CAR)
                .incidentAvgYear(1 + newRandom.nextDouble())
                .model("Any model")
                .make("Any make")
                .basePrice(1000.0 + newRandom.nextInt(1000))
                .build();
    }

    public static BasePrice generateBasePriceToFindTest(VehicleType type, String year, String model, String make){
        Random newRandom = new Random();
        return BasePrice.builder()
                .year(year)
                .type(type)
                .incidentAvgYear(1 + newRandom.nextDouble())
                .model(model)
                .make(make)
                .basePrice(1000.0 + newRandom.nextInt(1000))
                .build();
    }

    public static BasePrice generateBasePriceToFindTest(VehicleType type, String year, String make){
        Random newRandom = new Random();
        return BasePrice.builder()
                .year(year)
                .type(type)
                .incidentAvgYear(1 + newRandom.nextDouble())
                .model("Any model")
                .make(make)
                .basePrice(1000.0 + newRandom.nextInt(1000))
                .build();
    }

    public static BasePrice generateBasePriceToFindTest(VehicleType type, String year){
        Random newRandom = new Random();
        return BasePrice.builder()
                .year(year)
                .type(type)
                .incidentAvgYear(1 + newRandom.nextDouble())
                .model("Any model")
                .make("Any make")
                .basePrice(1000.0 + newRandom.nextInt(1000))
                .build();
    }

    public static BasePrice generateBasePriceToFindTest(VehicleType type){
        Random newRandom = new Random();
        return BasePrice.builder()
                .year(String.valueOf(2000 + newRandom.nextInt(18)))
                .type(type)
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
                .manufacturingYear(String.valueOf(1990 + newRandom.nextInt(28)))
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
