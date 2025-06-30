package com.pcwk.ehr.drive;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.mapper.DriveResMapper;

/**
 * DriveResMapper CRUD 및 조회 기능 검증 테스트
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class DriveResDaoTest {
    Logger log = LogManager.getLogger(getClass());

    @Autowired
    DriveResMapper mapper; // MyBatis 매퍼 :contentReference[oaicite:0]{index=0}

    @Autowired
    JdbcTemplate jdbcTemplate; // 테이블 초기화용

    DriveResDTO dto01;
    DriveResDTO dto02;
    DriveResDTO dto03;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setUp() throws Exception {
        // 1) 테이블 초기화
        jdbcTemplate.update("DELETE FROM drive_res");
        log.debug("drive_res 테이블 초기화");
        int carCode = jdbcTemplate.queryForObject(
        	    "SELECT car_code FROM car WHERE ROWNUM = 1", Integer.class);
        
        int retailerCode = jdbcTemplate.queryForObject(
        	    "SELECT retailer_code FROM retailer WHERE ROWNUM = 1", Integer.class);

        // 2) 테스트 데이터 준비 (driveDate 고정) :contentReference[oaicite:1]{index=1}
        Date driveDate = sdf.parse("2025-07-01");
        dto01 = new DriveResDTO("user01", "가민경", "010-1111-2222",
        		carCode, retailerCode, driveDate,  "admin",  "admin");
        dto02 = new DriveResDTO("user02", "나민경", "010-2222-3333",
        		10, 35, driveDate,  "tester",  "tester");
        dto03 = new DriveResDTO("user03", "다민경", "010-3333-4444",
        		10, 35, driveDate,  "demo",  "demo");
        log.debug("데이터 준비왈료왈왈");
    }

    @AfterEach
    public void tearDown() {
        log.debug("--------- @AfterEach ---------");
    }
    
    
  //  @Disabled
    @Test

    void doSave() throws Exception {
        // 1) 단건 저장
        int flag = mapper.doSave(dto01);
        assertEquals(1, flag, "doSave 성공 여부 확인");
        // 2) selectKey로 채번된 resNo가 DTO에 들어왔는지 검증
        assertTrue(dto01.getResNo() > 0, "시퀀스로 채번된 resNo는 0보다 커야 함");
    }

    //@Disabled
    @Test
    void doSelectOne() throws Exception {
        // 1) 사전 저장 (조회용 데이터)
        mapper.doSave(dto01);
        assertTrue(dto01.getResNo() > 0, "사전 저장 후 resNo 채번 확인");
        // 2) 단건 조회
        DriveResDTO out = mapper.doSelectOne(dto01);
        assertNotNull(out, "doSelectOne 결과는 null이 아니어야 함");
        // 3) 필드 값 비교
        isSameDriveRes(out, dto01);
    }

    
    
    //@Disabled
    @Test
    void doUpdate() throws Exception {
        // 1) 사전 저장
        mapper.doSave(dto01);
        // 2) 변경
        dto01.setName("홍길동_U");
        dto01.setPhone("010-9999-8888");
        dto01.setCarCode(11);
        dto01.setRetailerCode(37);
        dto01.setModId("upd");
        // 3) 업데이트
        int flag = mapper.doUpdate(dto01);
        assertEquals(1, flag, "doUpdate 성공 여부 확인"); // :contentReference[oaicite:3]{index=3}

        // 4) 변경 후 조회
        DriveResDTO up = mapper.doSelectOne(dto01);
        assertNotNull(up);
        assertEquals("홍길동_U", up.getName());
        assertEquals("010-9999-8888", up.getPhone());
        assertEquals(11, up.getCarCode());
        assertEquals(37, up.getRetailerCode());
        assertEquals("upd", up.getModId());
    }

   // @Disabled
    @Test
    void doDelete() throws Exception {
        // 1) 사전 저장
        mapper.doSave(dto01);
        // 2) 삭제
        int flag = mapper.doDelete(dto01);
        assertEquals(1, flag, "doDelete 성공 여부 확인"); // :contentReference[oaicite:4]{index=4}

        // 3) 삭제 후 조회 (null 반환 예상)
        DriveResDTO out = mapper.doSelectOne(dto01);
        assertNull(out, "삭제 후 doSelectOne은 null이어야 함");
    }

 // @Disabled
    @Test
    void doRetrieve() throws Exception {
        // 1) 다건 저장
        mapper.doSave(dto01);
        mapper.doSave(dto02);
        mapper.doSave(dto03);
        // 2) 목록 조회
        List<DriveResDTO> list = mapper.doRetrieve(null);
        assertEquals(3, list.size(), "doRetrieve 조회 건수 확인"); // :contentReference[oaicite:5]{index=5}

        for (DriveResDTO vo : list) {
            log.debug(vo);
        }
    }

    /** 필드 비교 헬퍼 (regDt, modDt 제외) */
    private void isSameDriveRes(DriveResDTO out, DriveResDTO in) {
        assertEquals(in.getResNo(), out.getResNo());
        assertEquals(in.getId(), out.getId());
        assertEquals(in.getName(), out.getName());
        assertEquals(in.getPhone(), out.getPhone());
        assertEquals(in.getCarCode(), out.getCarCode());
        assertEquals(in.getRetailerCode(), out.getRetailerCode());
        assertEquals(in.getDriveDate(), out.getDriveDate());
        assertEquals(in.getRegId(), out.getRegId());
        assertEquals(in.getModId(), out.getModId());
    }
}
