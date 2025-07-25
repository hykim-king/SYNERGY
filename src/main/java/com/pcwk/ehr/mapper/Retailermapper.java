package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.retailer.RetailerDTO;

@Mapper
public interface Retailermapper extends WorkDiv<RetailerDTO> {
	int getRetailerSeq();

	List<RetailerDTO> getAll();

	RetailerDTO getOne(int retailerCode);

	List<RetailerDTO> doRetrieveByCarMfList(@Param("carMfList") List<String> carMfList);

	int getCount();

	int deleteAll() throws SQLException;

	List<RetailerDTO> getRetailersByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

	RetailerDTO getRetailerById(@Param("retailerCode") int retailerCode);

	 // ⭐ 페이징+검색 Map 기반
    List<RetailerDTO> getRetailersByPageWithSearch(Map<String, Object> params);

    int getRetailersCountWithSearch(Map<String, Object> params);




}
