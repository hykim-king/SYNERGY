package com.pcwk.ehr.car;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pcwk.ehr.mapper.CarMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class CarTest {
    Logger log = LogManager.getLogger(CarTest.class);

    @Autowired
    CarMapper carMapper;  

    @Test
    void carAll() {
        List<CarDTO> list = carMapper.getAll();
        assertNotNull(list);
        for (CarDTO dto : list) {
            log.info("차량: {}", dto);
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
        List<CarDTO> list = carMapper.doRetrieveByCarMfList(carMfs);
        assertNotNull(list);
        for (CarDTO dto : list) {
            log.info("조건조회 결과: {}", dto);
        }
    }

    @Test
    void carCount() {
        int count = carMapper.getCount();
        System.out.println("count: " + count);
        assertTrue(count >= 0);
    }

    @Test
    void carOne() {
    	int pk = 6; // 또는 7, 8 등 실제 DB에 있는 값으로!
    	CarDTO dto = carMapper.getOne(pk);
    	assertNotNull(dto); // 이제 NullPointerException 발생 안함!
        log.info("단건 조회 결과: {}", dto);
    }
    
    @Test
    void insertCarTest() {
        // dpm, battery는 null 허용, regDt, modDt는 java.util.Date로 넣기!
        CarDTO car = new CarDTO(
            1001,                    // carCode (int)
            "소나타",                  // productName (String)
            "현대",                    // carMf (String)
            "세단",                    // cartype (String)
            "fn",                     // orgFn (String)
            "modFn",                  // modFn (String)
            null,        				// path (String)
            2500,                     // price (int)
            "가솔린",                  // fuel (String)
            15.5,                     // ef (double) ← "15km/l" 아님!
            "2.0",                    // engine (String)
            null,                     // dpm (Integer, nullable)
            null,                     // battery (Double, nullable)
            20240701,                 // mfDt (int, 예: 20240701)
            new Date(),               // regDt (Date)
            "admin",                  // regId (String)
            new Date(),               // modDt (Date)
            "admin"                   // modId (String)
        );
        int result = carMapper.add(car);
        assertEquals(1, result);
    }
}