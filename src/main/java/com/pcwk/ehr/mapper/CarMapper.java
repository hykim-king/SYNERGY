package com.pcwk.ehr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.WorkDiv;

@Mapper
public interface CarMapper extends WorkDiv<CarDTO> {

	// 시퀀스
	int getCarCode();

	// 등록된 전체 자동차 수 카운트
	int getCount();

	// 전체 자동차 데이터 삭제
	void deleteAll();

	// 고유 번호로 단일 자동차 정보 조회
	CarDTO getOne(int carCode);

	// 브랜드별 조회
	List<CarDTO> getCarsByBrand(@Param("brand") String brand);

	// 검색 기능
	List<CarDTO> searchCars(@Param("keyword") String keyword);

	// 전체 리스트
	List<CarDTO> getAllCars();

	void insertCar(List<CarDTO> cars);
}
