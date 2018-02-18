package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.BasePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePriceRepository extends JpaRepository<BasePrice, Long> {
}
