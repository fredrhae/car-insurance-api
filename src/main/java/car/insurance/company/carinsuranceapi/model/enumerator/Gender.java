package car.insurance.company.carinsuranceapi.model.enumerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Gender {
    MALE("male"),
    FEMALE("female");

    private String description;

    Gender(String description) {
        this.description = description;
    }

    public static Gender fromDescription(String description) {
        return Stream.of(Gender.values())
                .filter( currentGender -> currentGender.description.equalsIgnoreCase(description))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public  String getDescription() {
        return this.description;
    }
}
