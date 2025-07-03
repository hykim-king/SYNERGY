package com.pcwk.ehr.car.service;

import java.util.List;

import com.pcwk.ehr.car.CarDTO;

public interface CarService{

	List<CarDTO> getAllCars();

	List<CarDTO> getCarsByBrand(String brand);

	CarDTO getCarById(int carCode);

	int save(CarDTO car);

	int deleteById(int carCode);

	int deleteAll();

}
