package com.pcwk.ehr.retailer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pcwk.ehr.mapper.Retailermapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { 
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class RetailerTest {
    Logger log = LogManager.getLogger(RetailerTest.class);

    @Autowired
    Retailermapper retailerMapper;

    private int retailerCode;

    @BeforeEach
    void setup() throws SQLException {
        // 기존 데이터 삭제 (권장)
        //retailerMapper.deleteAll();

        RetailerDTO initial = new RetailerDTO(
            0, "아반떼", "현대 강남대리점", "현대", "서울",
            "서울 강남구 테헤란로 123", "02-123-4567",
            new Date(System.currentTimeMillis()), "admin",
            new Date(System.currentTimeMillis()), "admin"
        );
        retailerMapper.doSave(initial);
        retailerCode = initial.getRetailerCode();
    }

    @Test
    void getOne() {
        System.out.println("테스트용 retailerCode = " + retailerCode);
        RetailerDTO dto = retailerMapper.getOne(retailerCode);
        System.out.println("테스트 조회 결과 → " + dto);
        assertNotNull(dto);
    }


    
    
    @Test
    void retailerall() {
        List<RetailerDTO> list = retailerMapper.getAll();
        assertNotNull(list);
        for (RetailerDTO dto : list) {
            log.info("리테일러: {}", dto);
        }
    }

    @Test
    void doRetrieveByCarMfListTest() {
        List<String> carMfs = Arrays.asList(
            "랜드로버", "르노", "렉서스", "폭스바겐", "푸조", "캐딜락", "링컨",
            "현대", "벤츠", "비엠더블유", "도요타", "포르쉐", "람보르기니",
            "쉐보레", "쌍용", "아우디", "볼보", "지프", "벤틀리", "혼다",
            "기아", "테슬라", "페라리", "포드", "미니"
        );
        List<RetailerDTO> list = retailerMapper.doRetrieveByCarMfList(carMfs);
        assertNotNull(list);
        for (RetailerDTO dto : list) {
            log.info("조건조회 결과: {}", dto);
        }
    }
    
    @Test
    void GetCount() throws SQLException {
        int count = retailerMapper.getCount();
        log.info("전체 레코드 개수: {}", count);
        assertTrue(count >= 0);
    }

    @Test
    void retailerOne() {
        // 위 setUp()에서 시퀀스 값 retailerCode로 저장했으므로, 이 값을 그대로 조회
        RetailerDTO dto = retailerMapper.getOne(retailerCode);
        assertNotNull(dto);
        log.info("단건 조회 결과: {}", dto);
    }
    
    
}


