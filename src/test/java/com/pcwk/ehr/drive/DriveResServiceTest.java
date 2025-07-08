package com.pcwk.ehr.drive;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pcwk.ehr.mapper.DriveResMapper;

public class DriveResServiceTest {

    Logger log = LogManager.getLogger(getClass());

    private DriveResServiceImpl service;
    private DriveResMapper mockMapper;

    @BeforeEach
    void setUp() {
        mockMapper = Mockito.mock(DriveResMapper.class);
        service = new DriveResServiceImpl();
        service.setMapper(mockMapper); // 수동 의존성 주입
    }

    @Test
    void testDoSave() {
        DriveResDTO dto = new DriveResDTO("user01", "가민경", "010-1111-2222",
                1001, 2001, Date.valueOf("2025-07-03"),
                Date.valueOf("2025-07-02"), "admin", Date.valueOf("2025-07-02"), "admin");

        when(mockMapper.doSave(dto)).thenReturn(1);

        int result = service.doSave(dto);

        log.debug("📝 testDoSave() result = {}", result);
        assertEquals(1, result);
    }

    @Test
    void testDoUpdate() {
        DriveResDTO dto = new DriveResDTO("user01", "김수정", "010-9999-8888",
                1001, 2001, Date.valueOf("2025-07-05"),
                Date.valueOf("2025-07-01"), "admin", Date.valueOf("2025-07-04"), "admin");

        when(mockMapper.doUpdate(dto)).thenReturn(1);
        when(mockMapper.doSelectOne(dto)).thenReturn(dto);

        int result = service.doUpdate(dto);

        log.debug("🛠️ testDoUpdate() result = {}", result);
        log.debug("    ↳ name: {}, phone: {}", dto.getName(), dto.getPhone());

        assertEquals(1, result);
        assertEquals("김수정", dto.getName());
        assertEquals("010-9999-8888", dto.getPhone());
    }

    @Test
    void testDoDelete() {
        DriveResDTO dto = new DriveResDTO();
        dto.setResNo(123);

        when(mockMapper.doDelete(dto)).thenReturn(1);

        int result = service.doDelete(dto);

        log.debug("🗑️ testDoDelete() result = {}", result);
        assertEquals(1, result);
    }
}