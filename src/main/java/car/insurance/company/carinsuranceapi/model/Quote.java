package car.insurance.company.carinsuranceapi.model;

import car.insurance.company.carinsuranceapi.model.enumerator.QuoteStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "quote")
public class Quote implements Serializable{

    private static final long serialVersionUID = 445814936981199936L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quot_id")
    private Long id;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "quot_status")
    @Builder.Default
    private QuoteStatus status = QuoteStatus.OPEN;

    @Column(name = "quot_price")
    private Double price;
}
