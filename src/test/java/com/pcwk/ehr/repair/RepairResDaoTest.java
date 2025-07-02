package com.pcwk.ehr.repair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.mapper.RepairResMapper;

/**
 * RepairResMapper 단건 저장 · 삭제 테스트
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class RepairResDaoTest {
    Logger log = LogManager.getLogger(getClass());

    @Autowired
    RepairResMapper mapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    RepairResDTO repair_dto01;
    RepairResDTO repair_dto02;
    RepairResDTO repair_dto03;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private int carCode;
    private int retailerCode;
    
    @BeforeEach
    public void setUp() throws Exception {
        // 테이블 초기화
        jdbcTemplate.update("DELETE FROM repair_res");
        log.debug("repair_res 테이블 초기화");
        int carCode = jdbcTemplate.queryForObject(
        	    "SELECT car_code FROM car WHERE ROWNUM = 1", Integer.class);
        
        int retailerCode = jdbcTemplate.queryForObject(
        	    "SELECT retailer_code FROM retailer WHERE ROWNUM = 1", Integer.class);
        
        // 테스트용 DTO 준비 (repairDate 고정)
        Date repairDate = sdf.parse("2025-07-02");
        repair_dto01 = new RepairResDTO("user01", "초보운전자", "010-1111-2222",
        		carCode, retailerCode, repairDate, "엔진 소음 점검",   // repairDesc
            "admin", "admin" );
        repair_dto02 = new RepairResDTO("user02", "운전자", "010-1111-2222",
        		carCode, retailerCode, repairDate, "모름",   // repairDesc
                "admin", "admin" );
        repair_dto03 = new RepairResDTO("user03", "나야나", "010-1111-2222",
        		carCode, retailerCode, repairDate, "점검",   // repairDesc
                "admin", "admin" );
        log.debug("--------- ! 정비신청 준비완료 ! ---------");
    }

    @AfterEach
    public void tearDown() {
        log.debug("--------- @AfterEach ---------");
    }

    // @Disabled
    @Test
    void doSave() throws Exception {
        // 단건 저장 (selectKey로 repairNo 자동 채번)
        int flag = mapper.doSave(repair_dto01);
        assertEquals(1, flag, "doSave 성공 여부 확인");
        assertTrue(repair_dto01.getRepairNo() > 0, "시퀀스 repairNo가 0보다 커야 함");
       
        //수정 확인
        RepairResDTO saved = mapper.doSelectOne(repair_dto01); // resNo로 조회
        isSameRepairRes(saved, repair_dto01); // 입력값과 DB조회값 비교
    }
    
    //@Disabled
    @Test
    void doSelectOne() throws Exception {
        // 사전 저장
        mapper.doSave(repair_dto01);

        // 단건 조회
        RepairResDTO out = mapper.doSelectOne(repair_dto01);
        assertNotNull(out, "doSelectOne 결과는 null이 아니어야 함");

        isSameRepairRes(repair_dto01, out);
    }
    
    
    // @Disabled
    @Test
    void doUpdate() throws Exception {
        mapper.doSave(repair_dto01);
        repair_dto01.setPhone("010-2222-3333");
        repair_dto01.setRepairDesc("냉각수 확인 요청_update");
        repair_dto01.setModId("updateUser");

        int flag = mapper.doUpdate(repair_dto01);
        assertEquals(1, flag, "doUpdate 성공 여부 확인");

        RepairResDTO out = mapper.doSelectOne(repair_dto01);
        assertEquals("010-2222-3333", out.getPhone());
        assertEquals("냉각수 확인 요청_update", out.getRepairDesc());
        assertEquals("updateUser", out.getModId());
    }
    
    
   // @Disabled
    @Test
    void doDelete() throws Exception {
        // 1) 사전 저장
        mapper.doSave(repair_dto01);
        assertTrue(repair_dto01.getRepairNo() > 0, "save 후 repairNo 자동 채번 확인");

        // 2) 삭제
        int flag = mapper.doDelete(repair_dto01);
        assertEquals(1, flag, "doDelete 성공 여부 확인");

        // 3) 삭제 후 조회 (null 반환 예상)
        RepairResDTO out = mapper.doSelectOne(repair_dto01);
        assertNull(out, "삭제 후 doSelectOne은 null이어야 함");
    }
    
    // @Disabled
    @Test
    void doRetrieve() throws Exception {
        // 1) 다건 저장
        mapper.doSave(repair_dto01);
        mapper.doSave(repair_dto02);
        mapper.doSave(repair_dto03);
        // 2) 목록 조회
        List<RepairResDTO> list = mapper.doRetrieve(null);
       assertEquals(3, list.size(), "doRetrieve 조회 건수 확인"); // :contentReference[oaicite:5]{index=5}

        for (RepairResDTO vo : list) {
            log.debug(vo);
        }
    }


    /** 두 DTO 필드값 비교 (regDt, modDt 제외) */
    private void isSameRepairRes(RepairResDTO in, RepairResDTO out) {
        assertEquals(in.getRepairNo(),     out.getRepairNo());
        assertEquals(in.getId(),           out.getId());
        assertEquals(in.getName(),         out.getName());
        assertEquals(in.getPhone(),        out.getPhone());
        assertEquals(in.getCarCode(),      out.getCarCode());
        assertEquals(in.getRetailerCode(), out.getRetailerCode());
        assertEquals(in.getRepairDate(),   out.getRepairDate());
        assertEquals(in.getRepairDesc(),   out.getRepairDesc());
        assertEquals(in.getRegId(),        out.getRegId());
        assertEquals(in.getModId(),        out.getModId());
    }
}
