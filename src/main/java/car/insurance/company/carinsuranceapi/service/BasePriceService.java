package car.insurance.company.carinsuranceapi.service;

import car.insurance.company.carinsuranceapi.model.BasePrice;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import car.insurance.company.carinsuranceapi.repository.BasePriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasePriceService {
    private static final Logger logger = LoggerFactory.getLogger(BasePriceService.class);
    private static final String LOG_HEADER = "[BASE PRICE CALC.]";

    public static final double DEFAULT_BASE_PRICE = 1000.0;

    private final BasePriceRepository basePriceRepository;

    @Autowired
    public BasePriceService(final BasePriceRepository basePriceRepository)
    {
        this.basePriceRepository = basePriceRepository;
    }

    public Double retrieveBasePrice(Vehicle vehicle) {
        List<BasePrice> basePriceList = basePriceRepository.findByTypeAndYearAndMakeAndModelAllIgnoreCase(
                vehicle.getType(),
                vehicle.getManufacturingYear(),
                vehicle.getMake(),
                vehicle.getModel()
        );

        if(basePriceList.isEmpty()) {
            basePriceList = basePriceRepository.findByTypeAndYearAndMakeAllIgnoreCase(
                    vehicle.getType(),
                    vehicle.getManufacturingYear(),
                    vehicle.getMake()
            );

            if(basePriceList.isEmpty()) {
                basePriceList = basePriceRepository.findByTypeAndYearAllIgnoreCase(
                        vehicle.getType(),
                        vehicle.getManufacturingYear()
                );

                if(basePriceList.isEmpty()) {
                    basePriceList = basePriceRepository.findByType(
                            vehicle.getType()
                    );
                }
            }
        }

        if(basePriceList.size() > 0){
            return basePriceList.get(0).getBasePrice();
        }

        return DEFAULT_BASE_PRICE;
    }
}
