package com.pcwk.ehr.car;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
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
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })


@Transactional //롤백
class CarTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	CarMapper carMapper;

	List<CarDTO> cars;

	CarDTO car1;
	CarDTO car2;
	CarDTO car3;
	@BeforeEach
	void setUp() throws Exception {
		carMapper.deleteAll();

		// 각 샘플 데이터마다 시퀀스 값을 받아서 carCode 부여
		car1 = new CarDTO();
		//car1.setCarCode(carMapper.getCarCode());
		car1.setProductName("K5");
		car1.setCarMf("Kia");
		car1.setCartype("세단");
		car1.setOrgFn("org1.jpg");
		car1.setModFn("mod1.jpg");
		car1.setPath("/img/k5.png");
		car1.setPrice(3500);
		car1.setFuel("G");
		car1.setEf(13.5);
		car1.setEngine("2.0");
		car1.setDpm(2000);
		car1.setBattery(0.0);
		car1.setMfDt(2022);
		car1.setRegDt(new Date());
		car1.setRegId("admin");
		car1.setModDt(new Date());
		car1.setModId("admin");

		car2 = new CarDTO();
		//car2.setCarCode(carMapper.getCarCode());
		car2.setProductName("Sonata");
		car2.setCarMf("Hyundai");
		car2.setCartype("세단");
		car2.setOrgFn("org2.jpg");
		car2.setModFn("mod2.jpg");
		car2.setPath("/img/sonata.png");
		car2.setPrice(3200);
		car2.setFuel("G");
		car2.setEf(15.0);
		car2.setEngine("1.6");
		car2.setDpm(1600);
		car2.setBattery(0.0);
		car2.setMfDt(2021);
		car2.setRegDt(new Date());
		car2.setRegId("admin");
		car2.setModDt(new Date());
		car2.setModId("admin");

		car3 = new CarDTO();
		//car3.setCarCode(carMapper.getCarCode());
		car3.setProductName("520i");
		car3.setCarMf("BMW");
		car3.setCartype("세단");
		car3.setOrgFn("org3.jpg");
		car3.setModFn("mod3.jpg");
		car3.setPath("/img/520i.png");
		car3.setPrice(5500);
		car3.setFuel("D");
		car3.setEf(14.2);
		car3.setEngine("2.0");
		car3.setDpm(2000);
		car3.setBattery(0.0);
		car3.setMfDt(2023);
		car3.setRegDt(new Date());
		car3.setRegId("admin");
		car3.setModDt(new Date());
		car3.setModId("admin");

		cars = Arrays.asList(car1, car2, car3);

		for (CarDTO dto : cars) {
			log.debug(dto);
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		// carMapper.deleteAll();
	}

	@Test
	void doSave() {

		log.debug("before:{}",car1);
		int flag = carMapper.doSave(car1);
		log.debug("after:{}",car1);
		
		
		assertEquals(1, flag);

		CarDTO result = carMapper.doSelectOne(car1);
		assertEquals("K5", result.getProductName());
		assertEquals("Kia", result.getCarMf());
	}

	//@Disabled
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

	//@Disabled
	@Test
	void doDelete() {
		CarDTO dto = cars.get(0);
		int flag = carMapper.doDelete(dto);
		assertEquals(1, flag);
		assertEquals(2, carMapper.getCount());
	}
	//@Disabled
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
