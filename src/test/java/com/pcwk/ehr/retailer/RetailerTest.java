package com.pcwk.ehr.retailer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.Retailermapper;

@WebAppConfiguration // 가상 서블릿 컨텍스트 설정 명령어
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"

})
public class RetailerTest {
    Logger log = LogManager.getLogger(RetailerTest.class);

    @Autowired
    Retailermapper retailerMapper;

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
    void testGetCount() throws SQLException {
        int count = retailerMapper.getCount();
        log.info("전체 레코드 개수: {}", count);
        assertTrue(count >= 0);
    }

    @Test
    void retailerOne() {
        int pk = 1;
        RetailerDTO dto = retailerMapper.getOne(pk);
        assertNotNull(dto);
        log.info("단건 조회 결과: {}", dto);
    }
}