package com.pcwk.ehr.car;

import static org.junit.jupiter.api.Assertions.*;

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
    void getAllTest() {
        List<CarDTO> carList = carMapper.getAll();
        assertNotNull(carList, "전체 리스트 조회 결과가 null 입니다.");
        log.info("전체 차량 수 : {}", carList.size());
        for (CarDTO dto : carList) {
            log.info("차량 정보 : {}", dto);
        }
    }

    @Test
    void getOneTest() {
        int carCode = 1; 
        CarDTO car = carMapper.getOne(carCode);
        assertNotNull(car, "단건 조회 결과가 null 입니다. carCode=" + carCode);
        log.info("단건 조회 결과 : {}", car);
    }

    @Test
    void doRetrieveByCarMfListTest() {
        List<String> carMfs = Arrays.asList("현대", "기아", "벤츠");
        List<CarDTO> list = carMapper.doRetrieveByCarMfList(carMfs);
        assertNotNull(list, "제조사 목록 조건조회 결과가 null 입니다.");
        for (CarDTO dto : list) {
            log.info("제조사 조건조회 결과 : {}", dto);
        }
    }

    @Test
    void getCountTest() {
        int count = carMapper.getCount();
        log.info("전체 레코드 개수: {}", count);
        assertTrue(count >= 0);
    }
}
