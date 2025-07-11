package com.pcwk.ehr.car.service;

import java.util.List;
import com.pcwk.ehr.car.CarDTO;

/**
 * 차량(Car) 관련 서비스의 인터페이스입니다.
 * 비즈니스 로직에서 호출되며, 구현체는 주로 CarServiceImpl입니다.
 */
public interface CarService {

    /**
     * 모든 차량 정보를 조회합니다.
     * @return 전체 차량 목록
     */
    List<CarDTO> getAllCars();

    /**
     * 브랜드명으로 차량 목록을 조회합니다.
     * @param carMf 브랜드명(제조사)
     * @return 해당 브랜드의 차량 목록
     */
    List<CarDTO> getCarsByBrand(String carMf);

    /**
     * 차량 고유코드로 차량 정보를 조회합니다.
     * @param carCode 차량 고유코드
     * @return 해당 차량 정보
     */
    CarDTO getCarById(int carCode);

    /**
     * 새로운 차량 정보를 등록(저장)합니다.
     * @param car 저장할 차량 객체
     * @return 등록 성공 시 1, 실패 시 0
     */
    int save(CarDTO car);

    /**
     * 차량 정보를 수정(업데이트)합니다.
     * @param car 수정할 차량 객체
     * @return 수정 성공 시 1, 실패 시 0
     */
    int update(CarDTO car); // 수정 추가

    /**
     * 해당 차량 코드가 존재하는지 확인합니다.
     * @param carCode 확인할 차량 코드
     * @return 존재하면 true, 없으면 false
     */
    boolean existsById(int carCode); // 존재 여부 확인

    /**
     * 차량 코드를 통해 차량 정보를 삭제합니다.
     * @param carCode 삭제할 차량 코드
     * @return 삭제 성공 시 1, 실패 시 0
     */
    int deleteById(int carCode);

    /**
     * 모든 차량 정보를 일괄 삭제합니다.
     * @return 삭제된 행(레코드) 수
     */
    int deleteAll();

    /**
     * 전체 차량의 개수를 조회합니다.
     * @return 전체 차량 수
     */
    int getCarCount();

    /**
     * 페이징 처리를 통해 차량 목록을 조회합니다.
     * @param pageNum  페이지 번호(1부터 시작)
     * @param pageSize 한 페이지 당 차량 수
     * @return 해당 페이지의 차량 목록
     */
    List<CarDTO> getCarsByPage(int pageNum, int pageSize);

    /**
     * 차량 고유코드로 차량 정보를 조회합니다. (getCarById와 유사)
     * @param carCode 차량 코드
     * @return 차량 정보
     */
    CarDTO getOne(int carCode);

    /**
     * 검색어, 검색타입, 페이징을 반영한 차량 목록을 조회합니다.
     * @param pageNum 페이지 번호
     * @param pageSize 한 페이지 당 개수
     * @param searchType 검색타입(예: 브랜드, 모델명 등)
     * @param searchWord 검색어
     * @return 조건에 맞는 차량 목록
     */
    List<CarDTO> getCarsByPageWithSearch(int pageNum, int pageSize, String searchType, String searchWord);

    /**
     * 검색 조건에 해당하는 차량의 개수를 조회합니다.
     * @param searchType 검색타입
     * @param searchWord 검색어
     * @return 조건에 맞는 차량 수
     */
    int getCarCountWithSearch(String searchType, String searchWord);
}
