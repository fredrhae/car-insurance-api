package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findBySsnIgnoreCase(String ssn);
}
