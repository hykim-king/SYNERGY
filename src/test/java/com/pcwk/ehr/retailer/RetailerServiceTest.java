package com.pcwk.ehr.retailer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pcwk.ehr.retailer.service.RetailerServiceImpl;
import com.pcwk.ehr.mapper.Retailermapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { 
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" 
})
class RetailerServiceTest {

    @Autowired
    private Retailermapper retailerMapper;

    @Autowired
    private RetailerServiceImpl retailerService;

    private int retailerCode;

    @BeforeEach
    void setup() throws SQLException {
        // 기존 데이터 전체 삭제 (필요 시 주석 해제)
        retailerMapper.deleteAll();

        // 테스트용 DTO 생성
        RetailerDTO initial = new RetailerDTO(0, // dummy 코드
                "아반떼", 
                "현대 강남대리점", 
                "현대", 
                "서울", 
                "서울 강남구 테헤란로 123", 
                "02-123-4567", 
                new Date(System.currentTimeMillis()), 
                "admin", 
                new Date(System.currentTimeMillis()), 
                "admin");

        // 저장 및 시퀀스 코드 획득
        retailerMapper.doSave(initial);
        this.retailerCode = initial.getRetailerCode(); // 시퀀스 값으로 실제 PK 설정
    }

    @Test
    void getAll() {
        List<RetailerDTO> list = retailerService.getAll();
        assertNotNull(list);
        System.out.println("▶ getAll(): " + list);
    }

    @Test
    void getOne() {
        RetailerDTO dto = retailerService.getOne(retailerCode);
        assertNotNull(dto);
        System.out.println("▶ getOne(): " + dto);
    }

    @Test
    void doRetrieveByCarMfList() {
        List<String> brands = Arrays.asList("현대");
        List<RetailerDTO> list = retailerService.doRetrieveByCarMfList(brands);
        assertNotNull(list);
        System.out.println("▶ doRetrieveByCarMfList(): " + list);
    }

    @Test
    void doSave() {
        RetailerDTO newDto = new RetailerDTO(0, 
                "소나타", 
                "현대 서초대리점", 
                "현대", 
                "서울", 
                "서울 서초구 서초대로 456", 
                "02-987-6543", 
                new Date(System.currentTimeMillis()), 
                "testuser", 
                new Date(System.currentTimeMillis()), 
                "testuser");

        int result = retailerService.doSave(newDto);
        assertEquals(1, result);
        System.out.println("▶ doSave() 성공 여부: " + result);
    }

    @Test
    void doUpdate() {
        RetailerDTO updateDto = retailerService.getOne(retailerCode);
        updateDto.setRetailerName("현대 삼성지점");
        updateDto.setModId("tester");

        int result = retailerService.doUpdate(updateDto);
        assertEquals(1, result);
        System.out.println("▶ doUpdate() 성공 여부: " + result);
    }

    @Test
    void doDelete() {
        RetailerDTO dto = retailerService.getOne(retailerCode);
        int result = retailerService.doDelete(dto);
        assertEquals(1, result);
        System.out.println("▶ doDelete() 성공 여부: " + result);
    }

    @Test
    void getRetailerCount() {
        int count = retailerService.getRetailerCount();
        assertNotNull(count);
        System.out.println("▶ getRetailerCount(): " + count);
    }

    @Test
    void getRetailerSeq() {
        int seq = retailerService.getRetailerSeq();
        assertNotNull(seq);
        System.out.println("▶ getRetailerSeq(): " + seq);
    }

    @Test
    void getRetailersByPage() {
        List<RetailerDTO> list = retailerService.getRetailersByPage(1, 10);
        assertNotNull(list);
        System.out.println("▶ getRetailersByPage(): " + list);
    }

    @Test
    void getRetailerById() {
        RetailerDTO dto = retailerService.getRetailerById(retailerCode);
        assertNotNull(dto);
        System.out.println("▶ getRetailerById(): " + dto);
    }

    @Test
    void getAllRetailers() {
        List<RetailerDTO> list = retailerService.getAllRetailers();
        assertNotNull(list);
        System.out.println("▶ getAllRetailers(): " + list);
    }
}
