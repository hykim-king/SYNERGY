package com.pcwk.ehr.repair;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pcwk.ehr.mapper.RepairResMapper;

public class RepairResServiceTest {

    private final Logger log = LogManager.getLogger(getClass());

    private RepairResServiceImpl service;
    private RepairResMapper mockMapper;

    @BeforeEach
    void setUp() {
        mockMapper = Mockito.mock(RepairResMapper.class);
        service = new RepairResServiceImpl();
        service.setMapper(mockMapper); // 수동 의존성 주입
    }

    @Test
    void testDoSave() {
        RepairResDTO dto = new RepairResDTO("user01", "가민경", "010-2222-3333",
                1001, 2001, Date.valueOf("2025-07-03"), "엔진 점검 요청",
                Date.valueOf("2025-07-02"), "admin", Date.valueOf("2025-07-02"), "admin");

        when(mockMapper.doSave(dto)).thenReturn(1);

        int result = service.doSave(dto);

        log.debug("📝 testDoSave() result = {}", result);
        assertEquals(1, result);
    }

    @Test
    void testDoUpdate() {
        RepairResDTO dto = new RepairResDTO("user01", "변경된이름", "010-9999-8888",
                1001, 2001, Date.valueOf("2025-07-05"), "브레이크 점검",
                Date.valueOf("2025-07-02"), "admin", Date.valueOf("2025-07-04"), "admin");

        when(mockMapper.doUpdate(dto)).thenReturn(1);
        when(mockMapper.doSelectOne(dto)).thenReturn(dto);

        int result = service.doUpdate(dto);

        log.debug("🛠️ testDoUpdate() result = {}", result);
        log.debug("    ↳ name: {}, desc: {}", dto.getName(), dto.getRepairDesc());

        assertEquals(1, result);
        assertEquals("변경된이름", dto.getName());
        assertEquals("브레이크 점검", dto.getRepairDesc());
    }

    @Test
    void testDoDelete() {
        RepairResDTO dto = new RepairResDTO();
        dto.setRepairNo(123); // 삭제는 repairNo만 있으면 됨

        when(mockMapper.doDelete(dto)).thenReturn(1);

        int result = service.doDelete(dto);

        log.debug("🗑️ testDoDelete() result = {}", result);
        assertEquals(1, result);
    }
}