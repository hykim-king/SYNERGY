package com.pcwk.ehr.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.repair.RepairResDTO;
import com.pcwk.ehr.retailer.RetailerDTO;


public interface RepairResMapper extends WorkDiv<RepairResDTO> {

	List<String> mfList();

	List<CarDTO> productList(String carMf);

	List<RetailerDTO> retailerList(String productName);

	int getCarCode(@Param("carMf") String carMf,
					@Param("productName") String productName);
	
}