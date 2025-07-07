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

/**
 * CarServiceImpl 클래스는 CarService 인터페이스를 구현하며,
 * 비즈니스 로직을 처리하고 CarMapper를 통해 데이터베이스와 상호작용합니다.
 */
@Service
public class CarServiceImpl implements CarService {
    // 로거 인스턴스 (메서드 실행 시간 등 로깅)
    Logger log = LogManager.getLogger(getClass());

    // MyBatis Mapper를 통해 SQL 매핑된 메서드를 호출
    @Autowired
    private CarMapper carMapper;

    /**
     * 모든 차량 정보를 조회합니다.
     * @return List<CarDTO> 모든 차량 목록
     */
    @Override
    public List<CarDTO> getAllCars() {
        log.debug("getAllCars 호출");
        return carMapper.getAllCars();
    }

    /**
     * 브랜드로 차량 목록을 조회합니다.
     * @param brand 조회할 브랜드명
     * @return List<CarDTO> 해당 브랜드 차량 목록
     */
    @Override
    public List<CarDTO> getCarsByBrand(String brand) {
        log.debug("getCarsByBrand 호출: brand=" + brand);
        return carMapper.getCarsByBrand(brand);
    }

    /**
     * 특정 차량 코드를 기준으로 단일 차량을 조회합니다.
     * @param carCode 조회할 차량 코드
     * @return CarDTO 조회된 차량 정보
     */
    @Override
    public CarDTO getCarById(int carCode) {
        log.debug("getCarById 호출: carCode=" + carCode);
        return carMapper.getOne(carCode);
    }

    /**
     * 새로운 차량 정보를 저장(등록)합니다.
     * @param car 저장할 CarDTO 객체
     * @return int 등록 성공 시 1, 실패 시 0
     */
    @Override
    public int save(CarDTO car) {
        log.debug("save 호출: car=" + car);
        return carMapper.doSave(car);
    }

    /**
     * 기존 차량 정보를 업데이트(수정)합니다.
     * @param car 수정할 CarDTO 객체
     * @return int 수정 성공 시 1, 실패 시 0
     */
    @Override
    public int update(CarDTO car) {
        log.debug("update 호출: car=" + car);
        return carMapper.doUpdate(car);
    }

    /**
     * 주어진 차량 코드가 DB에 존재하는지 여부를 확인합니다.
     * @param carCode 확인할 차량 코드
     * @return boolean 존재 여부
     */
    @Override
    public boolean existsById(int carCode) {
        log.debug("existsById 호출: carCode=" + carCode);
        CarDTO car = carMapper.getOne(carCode);
        return car != null;
    }

    /**
     * 특정 차량 코드를 가진 데이터를 삭제합니다.
     * @param carCode 삭제할 차량 코드
     * @return int 삭제 성공 시 1, 실패 시 0
     */
    @Override
    public int deleteById(int carCode) {
        log.debug("deleteById 호출: carCode=" + carCode);
        return carMapper.doDeleteById(carCode);
    }

    /**
     * 모든 차량 데이터를 일괄 삭제합니다.
     * 주로 테스트나 초기화 용도로 사용됩니다.
     * @return int 삭제된 행(row) 수
     */
    @Override
    public int deleteAll() {
        log.debug("deleteAll 호출");
        return carMapper.deleteAll();
    }

    /**
     * 전체 차량 수(레코드 수)를 조회합니다.
     * 페이징 계산이나 통계용으로 사용됩니다.
     * @return int 전체 차량 수
     */
    @Override
    public int getCarCount() {
        log.debug("getCarCount 호출");
        return carMapper.getCarCount();
    }

    /**
     * 페이징 처리를 통해 특정 페이지 범위의 차량 목록을 조회합니다.
     * @param pageNum 조회할 페이지 번호 (1-based)
     * @param pageSize 페이지 당 데이터 개수
     * @return List<CarDTO> 해당 페이지의 차량 목록
     */
    @Override
    public List<CarDTO> getCarsByPage(int pageNum, int pageSize) {
        log.debug("getCarsByPage 호출: pageNum=" + pageNum + ", pageSize=" + pageSize);
        int offset = (pageNum - 1) * pageSize;  // 조회 시작 인덱스 계산
        Map<String, Integer> params = new HashMap<>();
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        return carMapper.getCarsByPage(params);
    }

    /**
     * 단일 차량 정보를 조회하는 getOne 메서드의 alias 역할을 합니다.
     * @param carCode 조회할 차량 코드
     * @return CarDTO 조회된 차량 정보
     */
    @Override
    public CarDTO getOne(int carCode) {
        log.debug("getOne 호출: carCode=" + carCode);
        return carMapper.getOne(carCode);
    }

}
