package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
