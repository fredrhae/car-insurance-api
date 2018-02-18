package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
