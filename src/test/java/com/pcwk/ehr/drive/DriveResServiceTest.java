package com.pcwk.ehr.drive;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pcwk.ehr.mapper.DriveResMapper;

public class DriveResServiceTest {

    private DriveResServiceImpl service;
    private DriveResMapper mockMapper;

    @BeforeEach
    void setUp() {
        mockMapper = Mockito.mock(DriveResMapper.class);
        service = new DriveResServiceImpl();
        service.setMapper(mockMapper); // DI
    }

    @Test
    void testDoSave() {
        DriveResDTO dto = new DriveResDTO("user01", "가민경", "010-1111-2222",
                1001, 2001, Date.valueOf("2025-07-03"), Date.valueOf("2025-07-02"), "admin",
                Date.valueOf("2025-07-02"), "admin");

        when(mockMapper.doSave(dto)).thenReturn(1);

        int result = service.doSave(dto);

        assertEquals(1, result);
    }
}