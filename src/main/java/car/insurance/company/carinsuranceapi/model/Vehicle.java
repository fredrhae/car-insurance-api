package car.insurance.company.carinsuranceapi.model;

import car.insurance.company.carinsuranceapi.model.enumerator.VehicleType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable{

    private static final long serialVersionUID = -6365510940294707438L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehi_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehi_type")
    private VehicleType type;

    @Column(name = "vehi_manufacturing_year")
    private LocalDate manufacturingYear;

    @Column(name = "vehi_model")
    private String model;

    @Column(name = "vehi_make")
    private String make;
}
