package car.insurance.company.carinsuranceapi.model.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum VehicleType {
    CAR("car"),
    MOTORCYCLE("motorcycle"),
    TRUCK("truck"),
    OTHER("other");

    private String description;

    VehicleType(String description){
        this.description = description;
    }

    public static VehicleType fromDescription(String description){
        return Stream.of(VehicleType.values())
                .filter(vehicleType -> vehicleType.description.equalsIgnoreCase(description))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getDescription(){
        return this.description;
    }
}
