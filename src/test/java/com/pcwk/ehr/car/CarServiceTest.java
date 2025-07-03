package com.pcwk.ehr.car;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pcwk.ehr.car.service.CarServiceImpl;
import com.pcwk.ehr.mapper.CarMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
class CarServiceTest {

    @Autowired
    private CarMapper   carMapper;

    @Autowired
    private CarServiceImpl carService;

    private int carCode;

    @BeforeEach
    void setup() throws SQLException {
        // 1) 기존 데이터 전체 삭제
       // carMapper.deleteAll();

        // 2) 테스트용 CarDTO 생성 (시퀀스는 doSave의 selectKey가 처리)
        CarDTO initial = new CarDTO(
            0,                                // ▶ dummy; 실제 PK는 doSave selectKey가 채워줍니다
            "현대",                           // PRODUCT_NAME
            "아반떼",                             // CAR_MF
            "세단",                             // CARTYPE
            null,                              // ORG_FN
            null,                              // MOD_FN
            null,                              // PATH
            2000,                              // PRICE
            "가솔린",                           // FUEL
            15.2,                              // EF
            null,                              // ENGINE
            null,                              // DPM
            null,                              // BATTERY
            2020,                              // MF_DT (정수)
            new Date(System.currentTimeMillis()), // REG_DT
            "admin",                           // REG_ID
            new Date(System.currentTimeMillis()), // MOD_DT
            "admin"                            // MOD_ID
        );
        // 3) 삽입 (doSave의 selectKey가 initial.carCode를 세팅)
        carMapper.doSave(initial);

        // 4) 초기 데이터의 실제 PK를 꺼내 테스트에 사용
        this.carCode = initial.getCarCode();
    }

    @Test
    void getAllCars() {
        List<CarDTO> cars = carService.getAllCars();
        assertNotNull(cars);
    }

    @Test
    void getCarsByBrand() {
        List<CarDTO> cars = carService.getCarsByBrand("현대");
        assertNotNull(cars);
    }

    @Test
    void getCarById() {
        CarDTO car = carService.getCarById(carCode);
        System.out.println("테스트 조회 결과 → " + car);
        assertNotNull(car);
    }
}
