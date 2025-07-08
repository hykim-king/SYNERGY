package com.pcwk.ehr.drive;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;

public interface DriveResService {
    int doSave(DriveResDTO dto);
    DriveResDTO doSelectOne(DriveResDTO dto);
    int doUpdate(DriveResDTO dto);
    int doDelete(DriveResDTO dto);
    
    List<DriveResDTO> doRetrieve(DTO inVO);     // 목록 조회
    
    List<DriveResDTO> doRetrievePaging(DTO search) throws Exception;
    
    int getTotalCount(DTO dto) throws Exception;
}