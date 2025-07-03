package com.pcwk.ehr.car;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pcwk.ehr.car.service.CarService;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class CarControllerTest {
	@Autowired
	WebApplicationContext context;

	@Autowired
	MockMvc mockMvc;

	@Mock
	CarService carService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void getAllCars() throws Exception {
		CarDTO car1 = new CarDTO(1001, "아반떼", "현대", "세단", // carCode, productName, carMf, cartype
				null, null, null, // orgFn, modFn, path
				2500, "가솔린", 15.5, "V6", // price, fuel, ef, engine
				1999, 0.0, 2023, // dpm, battery, mfDt
				new Date(0), "admin", new Date(0), "admin" // regDt, regId, modDt, modId
		);
		CarDTO car2 = new CarDTO(1002, "K5", "기아", "세단", null, null, null, 2700, "디젤", 14.0, "I4", 1800, 0.0, 2022,
				new Date(0), "user1", new Date(0), "user1");
		Mockito.when(carService.getAllCars()).thenReturn(Arrays.asList(car1, car2));

		mockMvc.perform(get("/car/list")).andExpect(status().isOk()).andExpect(model().attributeExists("carList"))
				.andExpect(view().name("car/list"));
	}

	@Test
	void getCarsByBrand() throws Exception {
		CarDTO car1 = new CarDTO(1003, "쏘나타", "현대", "세단", null, null, null, 2600, "가솔린", 16.0, "I4", 2000, 0.0, 2024,
				new Date(0), "test", new Date(0), "test");

		Mockito.when(carService.getCarsByBrand("현대")).thenReturn(Arrays.asList(car1));

		mockMvc.perform(get("/car/brand").param("brand", "현대")).andExpect(status().isOk())
				.andExpect(model().attributeExists("carList")).andExpect(view().name("car/list"));
	}

	@Test
	void getCarById() throws Exception {
		CarDTO car1 = new CarDTO(1004, "EV6", "기아", "SUV", null, null, null, 4200, "전기", 5.3, null, 0, 77.4, 2023,
				new Date(0), "admin", new Date(0), "admin");

		org.mockito.Mockito.when(carService.getCarById(1003)).thenReturn(car1);

		mockMvc.perform(get("/car/detail/1003")).andExpect(status().isOk()).andExpect(model().attributeExists("car1"))
				.andExpect(view().name("car/detail"));
	}
}