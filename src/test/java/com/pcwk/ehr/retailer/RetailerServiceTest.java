package com.pcwk.ehr.retailer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pcwk.ehr.retailer.service.RetailerServiceImpl;
import com.pcwk.ehr.mapper.Retailermapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class RetailerServiceTest {

	@Autowired
	private Retailermapper retailerMapper;

	@Autowired
	private RetailerServiceImpl retailerService;

	private int retailerCode;

	@BeforeEach
	void setup() throws SQLException {
		// 1) 기존 데이터 전체 삭제
		// retailerMapper.deleteAll();

		// 2) 테스트용 RetailerDTO 생성 (시퀀스는 doSave의 selectKey가 처리)
		RetailerDTO initial = new RetailerDTO(0, // retailerCode: dummy, 실제 PK는 doSave selectKey에서 할당됨
				"아반떼", // productName
				"현대 강남대리점", // retailerName
				"현대", // carMf
				"서울", // area
				"서울 강남구 테헤란로 123", // address
				"02-123-4567", // telephone
				new Date(System.currentTimeMillis()), // regDt
				"admin", // regId
				new Date(System.currentTimeMillis()), // modDt
				"admin" // modId
		);

		// 3) 삽입 (doSave의 selectKey가 initial.retailerCode를 세팅)
		retailerMapper.doSave(initial);

		// 4) 초기 데이터의 실제 PK를 꺼내 테스트에 사용
		this.retailerCode = initial.getRetailerCode();
	}

	@Test
	void getAll() {
		List<RetailerDTO> list = retailerService.getAll();
		assertNotNull(list);
	}

	@Test
	void getOne() {
		RetailerDTO dto = retailerService.getOne(retailerCode);
		System.out.println("테스트 조회 결과 → " + dto);
		assertNotNull(dto);
	}

	@Test
	void doRetrieveByCarMfList() {
		List<String> brands = Arrays.asList("현대"); // ✅ 이렇게!
		List<RetailerDTO> list = retailerService.doRetrieveByCarMfList(brands);
		assertNotNull(list);
	}

}
