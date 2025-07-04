package com.pcwk.ehr.car;

import static org.junit.jupiter.api.Assertions.*;

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
        // 1) 기존 데이터 전체 삭제 (선택)
        // carMapper.deleteAll();

        // 2) 테스트용 CarDTO 생성
        CarDTO initial = new CarDTO(
            0, "현대", "아반떼", "세단", null, null, null,
            2000, "가솔린", 15.2, null, null, null, 2020,
            new Date(System.currentTimeMillis()), "admin",
            new Date(System.currentTimeMillis()), "admin"
        );
        // 3) 삽입 (doSave의 selectKey가 carCode 채움)
        carMapper.doSave(initial);

        // 4) 실제 PK 얻기
        this.carCode = initial.getCarCode();
    }

    @Test
    void getAllCars() {
        List<CarDTO> cars = carService.getAllCars();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }

    @Test
    void getCarsByBrand() {
    	List<CarDTO> cars = carService.getCarsByBrand("아반떼");
    	assertNotNull(cars);
    	assertTrue(cars.size() > 0);
    }

    @Test
    void getCarById() {
        CarDTO car = carService.getCarById(carCode);
        assertNotNull(car);
        assertEquals(carCode, car.getCarCode());
    }

    @Test
    void updateCar() {
        // 1) 데이터 조회
        CarDTO car = carService.getCarById(carCode);
        assertNotNull(car);

        // 2) 일부 데이터 변경
        car.setPrice(2500);
        car.setCartype("SUV");

        // 3) 수정
        int updated = carService.update(car);
        assertTrue(updated > 0);

        // 4) 재조회 및 검증
        CarDTO updatedCar = carService.getCarById(carCode);
        assertEquals(2500, updatedCar.getPrice());
        assertEquals("SUV", updatedCar.getCartype());
    }

    @Test
    void existsById() {
        boolean exists = carService.existsById(carCode);
        assertTrue(exists);

        boolean notExists = carService.existsById(999999);
        assertFalse(notExists);
    }

    @Test
    void deleteById() {
        // 1) 삭제
        int result = carService.deleteById(carCode);
        assertTrue(result > 0);

        // 2) 삭제 후 재조회
        CarDTO car = carService.getCarById(carCode);
        assertNull(car);
    }

    @Test
    void deleteAll() {
        // 1) 전체 삭제
        int deleted = carService.deleteAll();
        assertTrue(deleted >= 0);

        // 2) 재조회
        List<CarDTO> cars = carService.getAllCars();
        assertEquals(0, cars.size());
    }

    @Test
    void saveCar() {
        CarDTO newCar = new CarDTO(
            0, "기아", "K5", "세단", null, null, null,
            1800, "디젤", 13.5, null, null, null, 2022,
            new Date(System.currentTimeMillis()), "test",
            new Date(System.currentTimeMillis()), "test"
        );
        int result = carService.save(newCar);
        assertTrue(result > 0);
        assertTrue(newCar.getCarCode() > 0); // 시퀀스 PK가 잘 들어갔는지 체크
    }
}
