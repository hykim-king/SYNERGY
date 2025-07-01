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
        assertNotNull(list);  //null이면 절대 안 됨
        for (RetailerDTO dto : list) {
            log.info("리테일러: {}", dto);
        }
    }

    @Test
    // 여러 브랜드 중 자동차 데이터만 모두 조회
    void doRetrieveByCarMfListTest() {
        List<String> carMfs = Arrays.asList(
            "랜드로버", "르노", "렉서스", "폭스바겐", "푸조", "캐딜락", "링컨",
            "현대", "벤츠", "비엠더블유", "도요타", "포르쉐", "람보르기니",
            "쉐보레", "쌍용", "아우디", "볼보", "지프", "벤틀리", "혼다",
            "기아", "테슬라", "페라리", "포드", "미니"
        );
        List<RetailerDTO> list = retailerMapper.doRetrieveByCarMfList(carMfs);
        assertNotNull(list);  // null이면 절대 안됨.
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
        int pk = 117;  // 현재 developer 첫 번째 데이터의 시퀀스 값
        RetailerDTO dto = retailerMapper.getOne(pk);
        assertNotNull(dto); // 이제 null 아님 → 테스트 통과!
    }
    
    
    @BeforeEach
    void setUp() throws SQLException {
        retailerMapper.deleteAll();
        retailerMapper.doSave(
            new RetailerDTO(117, "K5", "카모터스", "Kia", "서울", "서울특별시 강남구", "02-1111-1111",
                new Date(System.currentTimeMillis()), "admin", new Date(System.currentTimeMillis()), "admin")
        );
    }
}