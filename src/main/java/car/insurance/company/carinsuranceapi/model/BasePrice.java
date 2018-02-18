package car.insurance.company.carinsuranceapi.model;

import car.insurance.company.carinsuranceapi.model.enumerator.VehicleType;
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
@Entity
@Table(name = "base_price")
public class BasePrice implements Serializable{

    private static final long serialVersionUID = 7617234791772085516L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bapr_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "bapr_vehicle_type")
    private VehicleType type;

    @Column(name = "bapr_manufacturing_year")
    private LocalDate year;

    @Column(name = "bapr_model")
    private String model;

    @Column(name = "bapr_make")
    private String make;

    @Column(name = "bapr_incident_avg_year")
    private Double incidentAvgYear;

    @Column(name = "bapr_base_price")
    private Double basePrice;
}
