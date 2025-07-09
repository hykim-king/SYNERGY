package com.pcwk.ehr.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.drive.DriveResDTO;
import com.pcwk.ehr.repair.RepairResDTO;
import com.pcwk.ehr.retailer.RetailerDTO;


public interface RepairResMapper extends WorkDiv<RepairResDTO> {
	// 제조사 목록 조회
	List<String> mfList();

	//선택한 제조사의 제품명 목록 조회
    List<CarDTO> productList(@Param("carMf") String carMf);

	//제품명으로 업체 목록 조회
	List<RetailerDTO> retailerList(@Param("productName") String productName);
	
	// 제조사+제품명으로 카코드 조회
	int getCarCode(@Param("carMf") String carMf,
					@Param("productName") String productName);
	
	
	/*	정비신청 결과화면 정리 */
    CarDTO getCarInfoByCode(int carCode);
    RetailerDTO getRetailerInfoByCode(int retailerCode);

    public List<RepairResDTO> doRetrieveByUser(RepairResDTO dto);
	
	
}