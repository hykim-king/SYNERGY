package com.pcwk.ehr.repair;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.RepairResMapper;

@Service
public class RepairResServiceImpl implements RepairResService {

    Logger log = LogManager.getLogger(getClass());

    @Autowired
    private RepairResMapper repairResMapper;

    public RepairResServiceImpl() {}
    
    public void setMapper(RepairResMapper repairResMapper) {
        this.repairResMapper = repairResMapper;
    }

    @Override
    public int doSave(RepairResDTO dto) {
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
            result = repairResMapper.doSave(dto);
            if (result > 0) {
                RepairResDTO saved = repairResMapper.doSelectOne(dto);
                dto.setRegDt(saved.getRegDt());
                dto.setModDt(saved.getModDt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int doUpdate(RepairResDTO dto) {
        if (dto.getModId() == null || dto.getModId().isEmpty()) {
            dto.setModId(dto.getName());
        }

        int result = repairResMapper.doUpdate(dto);
        if (result > 0) {
            RepairResDTO updated = repairResMapper.doSelectOne(dto);
            dto.setModDt(updated.getModDt());
        }

        return result;
    }

    @Override
    public int doDelete(RepairResDTO dto) {
        return repairResMapper.doDelete(dto);
    }

    @Override
    public RepairResDTO doSelectOne(RepairResDTO dto){
        return repairResMapper.doSelectOne(dto);
    }

    @Override
    public List<RepairResDTO> doRetrieve(DTO dto){
        return repairResMapper.doRetrieve(dto);
    }

	@Override
	public List<RepairResDTO> doRetrievePaging(DTO search) throws Exception {
		return repairResMapper.doRetrievePaging(search);
	}

	@Override
	public int getTotalCount(DTO dto) throws Exception {
		return repairResMapper.getTotalCount(dto);
	}
}