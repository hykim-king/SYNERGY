package com.pcwk.ehr.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.DTO;

@Mapper
public interface CarMapper {

	int getCarCode();

	int getCarCount();

	int deleteAll();

	CarDTO getOne(int carCode);

	CarDTO doSelectOne(CarDTO carDTO);

	List<CarDTO> getCarsByBrand(@Param("brand") String brand);

	List<CarDTO> searchCars(@Param("keyword") String keyword);

	List<CarDTO> getAllCars();

	void insertCar(List<CarDTO> cars);

	int doSave(CarDTO car);

	int doUpdate(CarDTO car);

	int doDeleteById(@Param("carCode") int carCode);

	int doDeleteAll();
	
	int doDelete(CarDTO car);

	List<CarDTO> getCarsByPage(Map<String, Integer> params);

	List<CarDTO> doRetrieve(DTO param);
}
