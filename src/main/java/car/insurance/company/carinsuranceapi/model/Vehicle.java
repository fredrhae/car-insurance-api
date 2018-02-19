package car.insurance.company.carinsuranceapi.model;

import car.insurance.company.carinsuranceapi.model.enumerator.VehicleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable{

    private static final long serialVersionUID = -6365510940294707438L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehi_id")
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehi_type")
    private VehicleType type;

    @Column(name = "vehi_manufacturing_year")
    @JsonProperty("manufacturing_year")
    private String manufacturingYear;

    @Column(name = "vehi_model")
    private String model;

    @Column(name = "vehi_make")
    private String make;
}
