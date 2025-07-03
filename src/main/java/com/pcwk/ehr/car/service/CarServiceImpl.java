package com.pcwk.ehr.car.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.mapper.CarMapper;

@Service
public class CarServiceImpl implements CarService {
	Logger log = LogManager.getLogger(getClass());

	public CarServiceImpl() {

	}

	@Autowired
	private CarMapper carMapper;

	@Override
	public List<CarDTO> getAllCars() {
	    List<CarDTO> list = carMapper.getAllCars();
	    list.forEach(car -> log.debug("▶ DB에 있는 차량 → {}", car));
	    return list;
	}

	@Override
	public List<CarDTO> getCarsByBrand(String brand) {

		return carMapper.getCarsByBrand(brand);
	}

	@Override
	public CarDTO getCarById(int carCode) {
	    return carMapper.getOne(carCode); // 파라미터를 그대로 넘기는지 확인!
	}

	@Override
	public int save(CarDTO car) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(int carCode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

}
