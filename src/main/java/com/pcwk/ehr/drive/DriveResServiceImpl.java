package com.pcwk.ehr.drive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.mapper.DriveResMapper;

@Service  // ↖ Component-scan으로 빈 등록
public class DriveResServiceImpl implements DriveResService {

    @Autowired
    private DriveResMapper driveResMapper;

    @Override
    public int doSave(DriveResDTO dto) throws Exception {
        // (추후 트랜잭션 처리나 다른 로직이 추가되더라도 여기서 관리)
        return driveResMapper.doSave(dto);
    }
}