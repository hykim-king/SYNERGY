package com.pcwk.ehr.car.service;

import java.util.List;
import com.pcwk.ehr.car.CarDTO;

public interface CarService {

    List<CarDTO> getAllCars();

    List<CarDTO> getCarsByBrand(String carMf);

    CarDTO getCarById(int carCode);

    int save(CarDTO car);

    int update(CarDTO car); // 수정 추가

    boolean existsById(int carCode); // 존재 여부 확인

    int deleteById(int carCode);

    int deleteAll();

    int getCarCount();
    

    List<CarDTO> getCarsByPage(int pageNum, int pageSize);

   	CarDTO getOne(int carCode);
   	
   	 List<CarDTO> getCarsByPageWithSearch(int pageNum, int pageSize, String searchType, String searchWord);

   	 int getCarCountWithSearch(String searchType, String searchWord);
   }