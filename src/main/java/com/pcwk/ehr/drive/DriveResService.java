package com.pcwk.ehr.drive;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.drive.DriveResDTO;
import com.pcwk.ehr.repair.RepairResDTO;

import java.util.List;

public interface DriveResService {
    int doSave(DriveResDTO dto);
    DriveResDTO doSelectOne(DriveResDTO dto);
    int doUpdate(DriveResDTO dto);
    int doDelete(DriveResDTO dto);
    
    List<DriveResDTO> doRetrieve(DTO inVO);     // 목록 조회
}