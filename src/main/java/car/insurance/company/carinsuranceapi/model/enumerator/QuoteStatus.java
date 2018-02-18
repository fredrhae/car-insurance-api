package car.insurance.company.carinsuranceapi.model.enumerator;

import java.util.stream.Stream;

public enum QuoteStatus {
    OPEN("open"),
    PROCESSING("processing"),
    DONE("done");

    private final String description;

    QuoteStatus(String description){
        this.description = description;
    }

    public static QuoteStatus fromDescription(String description){
        return Stream.of(QuoteStatus.values())
                .filter(quoteStatus -> quoteStatus.description.equalsIgnoreCase(description))
                .findFirst()
                .orElse(null);
    }

    public String getDescription(){
        return this.description;
    }
}
