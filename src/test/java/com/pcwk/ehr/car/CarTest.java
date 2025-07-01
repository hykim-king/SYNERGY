package com.pcwk.ehr.car;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.CarMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { 
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})

@Transactional
class CarTest {
    Logger log = LogManager.getLogger(getClass());

    @Autowired
    ApplicationContext context;

    @Autowired
    CarMapper carMapper;

    List<CarDTO> cars;

    @BeforeEach
    void setUp() throws Exception {
        carMapper.deleteAll();

        cars = Arrays.asList(
            new CarDTO(1001, "K5", "Kia", "세단", "org1.jpg", "mod1.jpg", "/img/k5.png",
                3500, "G", 13.5, "2.0", 2000, 0.0, 2022, new Date(), "admin", new Date(), "admin"),
            new CarDTO(1002, "Sonata", "Hyundai", "세단", "org2.jpg", "mod2.jpg", "/img/sonata.png",
                3200, "G", 15.0, "1.6", 1600, 0.0, 2021, new Date(), "admin", new Date(), "admin"),
            new CarDTO(1003, "520i", "BMW", "세단", "org3.jpg", "mod3.jpg", "/img/520i.png",
                5500, "D", 14.2, "2.0", 2000, 0.0, 2023, new Date(), "admin", new Date(), "admin")
        );

        for (CarDTO dto : cars) {
            carMapper.doSave(dto);
        }
    }

    @AfterEach
    void tearDown() throws Exception {
//        carMapper.deleteAll();
    }

    @Test
    @Commit
    void doSave() {
        CarDTO dto = new CarDTO();
        dto.setCarCode(2001);
        dto.setProductName("그랜저");
        dto.setCarMf("Hyundai");
        dto.setCartype("세단");
        dto.setOrgFn("org_grandeur.jpg");
        dto.setModFn("mod_grandeur.jpg");
        dto.setPath("/img/grandeur.png");
        dto.setPrice(4100);
        dto.setFuel("G");
        dto.setEf(12.5);
        dto.setEngine("2.5");
        dto.setDpm(2500);
        dto.setBattery(0.0);
        dto.setMfDt(2024);
        dto.setRegDt(new Date());
        dto.setRegId("admin");
        dto.setModDt(new Date());
        dto.setModId("admin");

        int flag = carMapper.doSave(dto);
        assertEquals(1, flag);

        CarDTO result = carMapper.doSelectOne(dto);
        assertEquals("그랜저", result.getProductName());
        assertEquals("Hyundai", result.getCarMf());
    }

    @Test
    void doUpdate() {
        CarDTO dto = cars.get(0);
        dto.setProductName("K5");
        dto.setPrice(3700);

        int flag = carMapper.doUpdate(dto);
        assertEquals(1, flag);

        CarDTO updated = carMapper.doSelectOne(dto);
        assertEquals("K5", updated.getProductName());
        assertEquals(3700, updated.getPrice());
    }

    @Test
    void doDelete() {
        CarDTO dto = cars.get(0);
        int flag = carMapper.doDelete(dto);
        assertEquals(1, flag);
        assertEquals(2, carMapper.getCount());
    }

    @Test
    void doRetrieve() {
        DTO param = new DTO();
        param.setSearchDiv("carMf");
        param.setSearchWord("Kia");
        List<CarDTO> result = carMapper.doRetrieve(param);

        assertEquals(1, result.size());
        assertEquals("K5", result.get(0).getProductName());
    }
}
