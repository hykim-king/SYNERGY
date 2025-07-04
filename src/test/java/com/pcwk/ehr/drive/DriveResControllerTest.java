package com.pcwk.ehr.drive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class DriveResControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	// ✅ 1. 시승 신청 등록 성공 테스트
	// @Disabled
	@Test
	void testApplyDriveSuccess() throws Exception {
		mockMvc.perform(
				post("/drive/apply.do").param("id", "testuser01").param("name", "홍길동").param("phone", "010-1234-5678")
						.param("carCode", "67").param("retailerCode", "20001").param("driveDate", "2025-07-01"))
				.andExpect(status().isOk()).andExpect(view().name("drive/driveResult"))
				.andExpect(model().attributeExists("dto")).andExpect(model().attribute("success", true));
	}

	// ✅ 2. 시승 신청 목록 조회
	@Disabled
	@Test
	void testListDrives() throws Exception {
		mockMvc.perform(get("/drive/list.do")).andExpect(status().isOk()).andExpect(view().name("drive/driveList"))
				.andExpect(model().attributeExists("driveList"));
	}

	// ✅ 3. 시승 신청 상세 조회 -> 
	@Disabled
	@Test
	void testDetailDrive() throws Exception {
		mockMvc.perform(get("/drive/detail.do").param("resNo", "46")) // 존재하는 resNo여야 함
				.andExpect(status().isOk()).andExpect(view().name("drive/driveDetail"))
				.andExpect(model().attributeExists("drive"));
	}
	
	// ✅ 4. 시승 신청 수정 테스트
	@Disabled
	@Test
	void testDriveUpdate() throws Exception {
	    mockMvc.perform(post("/drive/update.do")
	            .param("resNo", "1")
	            .param("name", "수정된이름")
	            .param("phone", "010-9999-0000")
	            .param("carCode", "67")
	            .param("retailerCode", "20001")
	            .param("driveDate", "2025-07-05")
	            .param("modId", "admin"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("drive/driveResult")) // 실제 뷰 이름에 맞게 수정 가능
	        .andExpect(model().attributeExists("dto"))
	        .andExpect(model().attribute("success", true));
	}

	// ✅ 5. 시승 신청 삭제 테스트
	@Disabled
	@Test
	void testDriveDelete() throws Exception {
	    mockMvc.perform(get("/drive/delete.do") // 또는 post(...)
	            .param("resNo", "1"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("drive/driveResult")) // 삭제 후 결과 페이지
	        .andExpect(model().attribute("success", true));
	}
	
	

}