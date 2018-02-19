package car.insurance.company.carinsuranceapi.repository;

import car.insurance.company.carinsuranceapi.model.BasePrice;
import car.insurance.company.carinsuranceapi.model.enumerator.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasePriceRepository extends JpaRepository<BasePrice, Long> {
    List<BasePrice> findByTypeAndYearAndMakeAndModelAllIgnoreCase(VehicleType type, String year, String make, String model);
    List<BasePrice> findByTypeAndYearAndMakeAllIgnoreCase(VehicleType type, String year, String make);
    List<BasePrice> findByTypeAndYearAllIgnoreCase(VehicleType type, String year);
    List<BasePrice> findByType(VehicleType type);
}
