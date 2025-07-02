package com.pcwk.ehr.retailer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pcwk.ehr.mapper.Retailermapper;

@WebAppConfiguration // 가상 서블릿 컨텍스트 설정 명령어
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class RetailerAdminDaoTest {

	Logger log = LogManager.getLogger(RetailerTest.class);

	@Autowired
	Retailermapper retailerMapper;

	@Autowired
	ApplicationContext context;

	RetailerDTO dto01;

	@BeforeEach
	void setUp() throws Exception {
		LocalDate now = LocalDate.now();
		int seq = retailerMapper.getRetailerSeq();
		dto01 = new RetailerDTO(seq, "브레이크 오일", "XYZ오토", "KIA", "Busan", "부산 해운대구 88번지", "051-1234-5678",
				Date.valueOf(now), "admin01", Date.valueOf(now), "admin01");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void doSave() throws SQLException {
		retailerMapper.deleteAll();

		int flag = retailerMapper.doSave(dto01);
		assertEquals(1, flag);

	}

	@Test
	public void doUpdate() throws SQLException {
		retailerMapper.deleteAll();

		retailerMapper.doSave(dto01);

		// 2) 수정할 데이터 셋팅 (예: 전화번호 변경)
		dto01.setTelephone("051-9876-5432");
		dto01.setModDt(Date.valueOf(LocalDate.now()));
		dto01.setModId("admin02");

		// 3) 업데이트 호출
		int updateFlag = retailerMapper.doUpdate(dto01);
		assertEquals(1, updateFlag);

		// 4) 실제 수정됐는지 다시 조회
		RetailerDTO updatedDto = retailerMapper.getOne(dto01.getRetailerCode());
		assertEquals("051-9876-5432", updatedDto.getTelephone());
		assertEquals("admin02", updatedDto.getModId());

	}

	@Test
	public void doDelete() throws SQLException {
		retailerMapper.deleteAll();

		retailerMapper.doSave(dto01);

		int deleteFlag = retailerMapper.doDelete(dto01);
		assertEquals(1, deleteFlag);

		RetailerDTO deleted = retailerMapper.getOne(dto01.getRetailerCode());
		assertEquals(null, deleted); // 또는 assertNull(deleted);
	}

	@Test
	void doRetrieveByCarMfListTest() {
		List<String> carMfs = Arrays.asList("랜드로버", "르노", "렉서스", "폭스바겐", "푸조", "캐딜락", "링컨", "현대", "벤츠", "비엠더블유", "도요타",
				"포르쉐", "람보르기니", "쉐보레", "쌍용", "아우디", "볼보", "지프", "벤틀리", "혼다", "기아", "테슬라", "페라리", "포드", "미니");
		List<RetailerDTO> list = retailerMapper.doRetrieveByCarMfList(carMfs);
		assertNotNull(list);
		for (RetailerDTO dto : list) {
			log.info("조건조회 결과: {}", dto);
		}
	}

	@Test
	void retailerOne() throws SQLException {
		retailerMapper.deleteAll();

		retailerMapper.doSave(dto01);

		RetailerDTO outDto = retailerMapper.getOne(dto01.getRetailerCode());

		assertNotNull(outDto);
		assertEquals(dto01.getRetailerCode(), outDto.getRetailerCode());

		log.debug("조회 결과: {}", outDto);

		log.debug("┌───────────────────────────────────────────┐");
		log.debug("│ doSelectOne 테스트 종료                      │");
		log.debug("└───────────────────────────────────────────┘");
	}

}
