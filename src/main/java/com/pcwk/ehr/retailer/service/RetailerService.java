package com.pcwk.ehr.retailer.service;

import java.util.List;

import com.pcwk.ehr.retailer.RetailerDTO;

public interface RetailerService {
	int getRetailerCount();

	int getRetailerSeq();

	List<RetailerDTO> getAll();

	RetailerDTO getOne(int retailerCode);

	List<RetailerDTO> doRetrieveByCarMfList(List<String> carMfList);

	int doSave(RetailerDTO dto);

	int doUpdate(RetailerDTO dto);

	int doDelete(RetailerDTO dto);

	List<RetailerDTO> getAllRetailers();

	RetailerDTO getRetailerById(int retailerCode);

	List<RetailerDTO> getRetailersByPage(int pageNum, int pageSize);

   	
   	 List<RetailerDTO> getRetailersByPageWithSearch(int pageNum, int pageSize, String searchType, String searchWord);

   	 int getRetailersCountWithSearch(String searchType, String searchWord);

}
