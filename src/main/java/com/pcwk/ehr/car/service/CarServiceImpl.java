package com.pcwk.ehr.car.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.mapper.CarMapper;

@Service
public class CarServiceImpl implements CarService {
    Logger log = LogManager.getLogger(getClass());

    @Autowired
    private CarMapper carMapper;

    @Override
    public List<CarDTO> getAllCars() {
        return carMapper.getAllCars();
    }

    @Override
    public List<CarDTO> getCarsByBrand(String brand) {
        return carMapper.getCarsByBrand(brand);
    }

    @Override
    public CarDTO getCarById(int carCode) {
        return carMapper.getOne(carCode);
    }

    @Override
    public int save(CarDTO car) {
        return carMapper.doSave(car);
    }

    @Override
    public int update(CarDTO car) {
        return carMapper.doUpdate(car);
    }

    @Override
    public boolean existsById(int carCode) {
        CarDTO car = carMapper.getOne(carCode);
        return car != null;
    }

    @Override
    public int deleteById(int carCode) {
        return carMapper.doDeleteById(carCode);
    }

    @Override
    public int deleteAll() {
        return carMapper.deleteAll();
    }

    @Override
    public int getCarCount() {
        return carMapper.getCarCount();
    }

    @Override
    public List<CarDTO> getCarsByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        Map<String, Integer> params = new HashMap<>();
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        return carMapper.getCarsByPage(params);
    }

    @Override
    public CarDTO getOne(int carCode) {
        return carMapper.getOne(carCode);
    }


}
