package com.pcwk.ehr.retailer.service;

import com.pcwk.ehr.retailer.RetailerDTO;
import com.pcwk.ehr.mapper.Retailermapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
