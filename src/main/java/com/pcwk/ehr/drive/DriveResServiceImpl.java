package com.pcwk.ehr.drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.drive.DriveResDTO;
import com.pcwk.ehr.mapper.DriveResMapper;

@Service
public class DriveResServiceImpl implements DriveResService {

	
    @Autowired
    DriveResMapper mapper;

    public void setMapper(DriveResMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public int doSave(DriveResDTO dto) {

        return mapper.doSave(dto);
    }

    @Override
    public DriveResDTO doSelectOne(DriveResDTO dto) {
        return mapper.doSelectOne(dto);
    }

    @Override
    public int doUpdate(DriveResDTO dto) {
        return mapper.doUpdate(dto);
    }

    @Override
    public int doDelete(DriveResDTO dto) {
        return mapper.doDelete(dto);
    }

}
