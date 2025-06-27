package com.pcwk.ehr.car;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.car.CarDTO;

public interface CarMapper {
	//검색 조건을 기준으로 데이터를 조회
		List<CarDTO> doRetrieve(CarDTO param);
		

		//전체 데이터 조회
		List<CarDTO> getAll();
		

		//이건 정확하게 알아야 됨 !!
		// 현재 테이블에 저장된 레코드 수 조회
		int getCount() throws SQLException;
		
		
//		//전체 데이터를 일괄 저장
//		int saveAll();   

//		//특정 리테일러 정보 삭제
//		int doDelete(RetailerDTO param);
		
		
//		//특정 리테일러 정보 수정
//		int doUpdate(RetailerDTO param);
		
		
		
//		//테이블 내 모든 데이터 삭제
//		void deleteAll() throws SQLException;
		
}
