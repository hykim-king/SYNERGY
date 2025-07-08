package com.pcwk.ehr.drive;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.DriveResMapper;

@Service
public class DriveResServiceImpl implements DriveResService {

    Logger log = LogManager.getLogger(getClass());

    @Autowired
    private DriveResMapper driveResMapper;

    public DriveResServiceImpl() {}
    
    public void setMapper(DriveResMapper driveResMapper) {
        this.driveResMapper = driveResMapper;
    }

    @Override
    public int doSave(DriveResDTO dto) {
        Date now = new Date();
        dto.setRegDt(now);
        dto.setModDt(now);

        if (dto.getRegId() == null || dto.getRegId().isEmpty()) {
            dto.setRegId(dto.getName() != null && !dto.getName().isEmpty() ? dto.getName() : dto.getId());
        }
        if (dto.getModId() == null || dto.getModId().isEmpty()) {
            dto.setModId(dto.getName() != null && !dto.getName().isEmpty() ? dto.getName() : dto.getId());
        }

        int result = 0;
        try {
            result = driveResMapper.doSave(dto);
            if (result > 0) {
                DriveResDTO saved = driveResMapper.doSelectOne(dto);
                dto.setRegDt(saved.getRegDt());
                dto.setModDt(saved.getModDt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int doUpdate(DriveResDTO dto) {
        if (dto.getModId() == null || dto.getModId().isEmpty()) {
            dto.setModId(dto.getName());
        }

        int result = driveResMapper.doUpdate(dto);
        if (result > 0) {
            DriveResDTO updated = driveResMapper.doSelectOne(dto);
            dto.setModDt(updated.getModDt());
        }

        return result;
    }

    @Override
    public int doDelete(DriveResDTO dto) {
        return driveResMapper.doDelete(dto);
    }

    @Override
    public DriveResDTO doSelectOne(DriveResDTO dto) {
        return driveResMapper.doSelectOne(dto);
    }
    
    @Override
    public List<DriveResDTO> doRetrieve(DTO dto) {
        return driveResMapper.doRetrieve(dto);
    }
}