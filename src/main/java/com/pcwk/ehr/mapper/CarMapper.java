package com.pcwk.ehr.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.DTO;

@Mapper
public interface CarMapper {

	 // 자동차 고유 번호(CAR_CODE) 시퀀스 값 가져오기
    int getCarCode();

    // 전체 자동차 수 조회
    int getCarCount();

    // 전체 자동차 데이터 삭제
    int deleteAll();

    // 자동차 1건 조회 (carCode 기준)
    CarDTO getOne(int carCode);

    // 자동차 1건 조회 (CarDTO 필드 기준 검색)
    CarDTO doSelectOne(CarDTO carDTO);

    // 브랜드로 자동차 목록 조회
    List<CarDTO> getCarsByBrand(@Param("brand") String brand);

    // 키워드로 자동차 검색
    List<CarDTO> searchCars(@Param("keyword") String keyword);

    // 모든 자동차 목록 조회
    List<CarDTO> getAllCars();

    // 다수의 자동차 데이터 일괄 삽입
    void insertCar(List<CarDTO> cars);

    // 자동차 1건 저장
    int doSave(CarDTO car);

    // 자동차 1건 수정
    int doUpdate(CarDTO car);

    // carCode 기준으로 자동차 1건 삭제
    int doDeleteById(@Param("carCode") int carCode);

    // 자동차 전체 삭제
    int doDeleteAll();

    // CarDTO 조건으로 자동차 1건 삭제 (예: carMf 기준 등)
    int doDelete(CarDTO car);

    // 페이징 처리된 자동차 목록 조회 (offset, pageSize)
    List<CarDTO> getCarsByPage(Map<String, Integer> params);

    // 검색 및 조건 조회 (DTO 기준)
    List<CarDTO> doRetrieve(DTO param);
    
 // 검색어와 페이징 조건을 반영한 자동차 목록 조회
    List<CarDTO> getCarsByPageWithSearch(Map<String, Object> params);

    // 검색 조건에 따른 자동차 수 조회
    int getCarCountWithSearch(Map<String, Object> params);
}