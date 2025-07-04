package com.pcwk.ehr.repair;

import java.util.List;
import com.pcwk.ehr.cmn.DTO;

public interface RepairResService {
    
    int doSave(RepairResDTO inVO);       // 등록
    
    int doUpdate(RepairResDTO inVO);     // 수정
    
    int doDelete(RepairResDTO inVO);     // 삭제
    
    RepairResDTO doSelectOne(RepairResDTO inVO); // 단건 조회
    
    List<RepairResDTO> doRetrieve(DTO inVO);     // 목록 조회
}