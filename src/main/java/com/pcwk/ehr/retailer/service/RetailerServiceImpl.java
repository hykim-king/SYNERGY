package com.pcwk.ehr.retailer.service;

import com.pcwk.ehr.retailer.RetailerDTO;
import com.pcwk.ehr.mapper.Retailermapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RetailerServiceImpl implements RetailerService {
    Logger log = LogManager.getLogger(getClass());

    @Autowired
    private Retailermapper retailerMapper;

    @Override
    public int getRetailerCount() {
        int count = retailerMapper.getCount();
        log.debug("▶ 리테일러점 총 개수: {}", count);
        return count;
    }

    @Override
    public int getRetailerSeq() {
        int seq = retailerMapper.getRetailerSeq();
        log.debug("▶ 리테일러점 시퀀스 값: {}", seq);
        return seq;
    }

    @Override
    public List<RetailerDTO> getAll() {
        List<RetailerDTO> list = retailerMapper.getAll();
        list.forEach(r -> log.debug("▶ DB에 있는 리테일러점 → {}", r));
        return list;
    }

    @Override
    public RetailerDTO getOne(int retailerCode) {
        RetailerDTO dto = retailerMapper.getOne(retailerCode);
        log.debug("▶ 단일 리테일러점 조회: {}", dto);
        return dto;
    }

    @Override
    public List<RetailerDTO> doRetrieveByCarMfList(List<String> carMfList) {
        List<RetailerDTO> list = retailerMapper.doRetrieveByCarMfList(carMfList);
        log.debug("▶ 제조사 목록으로 리테일러점 조회, 결과 개수: {}", list.size());
        return list;
    }

    @Override
    public int doSave(RetailerDTO dto) {
        int result = retailerMapper.doSave(dto);
        log.debug("▶ 리테일러점 저장 결과: {}", result);
        return result;
    }

    @Override
    public int doUpdate(RetailerDTO dto) {
        int result = retailerMapper.doUpdate(dto);
        log.debug("▶ 리테일러점 수정 결과: {}", result);
        return result;
    }

    @Override
    public int doDelete(RetailerDTO dto) {
        int result = retailerMapper.doDelete(dto);
        log.debug("▶ 리테일러점 단일 삭제 결과: {}", result);
        return result;
    }

    @Override
    public List<RetailerDTO> getRetailersByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return retailerMapper.getRetailersByPage(offset, pageSize);
    }

    @Override
    public List<RetailerDTO> getAllRetailers() {
        return retailerMapper.getAll();
    }

    @Override
    public RetailerDTO getRetailerById(int retailerCode) {
        return retailerMapper.getOne(retailerCode);
    }



    @Override
    public List<RetailerDTO> getRetailersByPageWithSearch(int pageNum, int pageSize, String searchType, String searchWord) {
        log.debug("getRetailersByPageWithSearch 호출: pageNum={}, pageSize={}, searchType={}, searchWord={}",
                  pageNum, pageSize, searchType, searchWord);

        int offset = (pageNum - 1) * pageSize;

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        params.put("searchType", searchType);
        params.put("searchWord", searchWord);

        return retailerMapper.getRetailersByPageWithSearch(params);
    }


	@Override
	public int getRetailersCountWithSearch(String searchType, String searchWord) {
	    log.debug("getRetailersCountWithSearch 호출: searchType={}, searchWord={}", searchType, searchWord);

	    Map<String, Object> params = new HashMap<>();
	    params.put("searchType", searchType);
	    params.put("searchWord", searchWord);

	    return retailerMapper.getRetailersCountWithSearch(params);
	}

}
