package car.insurance.company.carinsuranceapi.model.enumerator;

import java.util.stream.Stream;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

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

    public  String getDescription() {
        return this.description;
    }
}
