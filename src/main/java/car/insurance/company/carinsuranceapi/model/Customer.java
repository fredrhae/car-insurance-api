package car.insurance.company.carinsuranceapi.model;

import car.insurance.company.carinsuranceapi.model.enumerator.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 8711217203792623989L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_ssn")
    private String ssn;

    @Column(name = "cust_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "cust_gender")
    private Gender gender;

    @Column(name = "cust_birth_date")
    private LocalDateTime birthDate;

    @Column(name = "cust_address")
    private String address;

    @Column(name = "cust_email")
    private String email;

    @Column(name = "cust_phone_number")
    private String phoneNumber;
}
