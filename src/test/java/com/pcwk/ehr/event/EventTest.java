package com.pcwk.ehr.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.EventMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class EventTest {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	EventMapper mapper;

	EventDTO dto01;
	SearchDTO search;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌──────────── setUp() ────────────┐");

		int seq = mapper.getEventSeq();

		dto01 = new EventDTO(String.valueOf(seq), // ecode
				"test@pcwk.com", "이벤트 제목", "20", "이벤트 내용입니다.", "테스터", 0, new Date(), "admin", new Date(), "admin");

		search = new SearchDTO();
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("└──────────── tearDown() ────────────┘");
	}
	@Disabled
	@Test
	void updateReadCnt() {
		mapper.deleteAll();

		int seq = mapper.getEventSeq();
		dto01.setEcode(String.valueOf(seq));
		dto01.setRegId("admin");

		mapper.doSave(dto01);

		EventDTO readParam = new EventDTO();
		readParam.setEcode(dto01.getEcode());
		readParam.setRegId("otherUser"); // 본인이 아닌 경우만 증가

		int flag = mapper.updateReadCnt(readParam);
		assertEquals(1, flag);
	}
	@Disabled
	@Test
	void doRetrieve() {
		mapper.deleteAll();
		int count = mapper.saveAll();
		assertEquals(100, count);

		search.setPageNo(1);
		search.setPageSize(10);
		search.setSearchDiv("10");
		search.setSearchWord("제목");

		List<EventDTO> list = mapper.doRetrieve(search);
		assertNotNull(list);
		assertEquals(10, list.size());

		list.forEach(vo -> log.debug("vo={}", vo));
	}

	@Disabled
	@Test
	void doDelete() {
		mapper.deleteAll();
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		flag = mapper.doDelete(dto01);
		assertEquals(1, flag);

		assertEquals(0, mapper.getCount());
	}

	@Disabled
	@Test
	void doUpdate() {
		mapper.deleteAll();

		// 1. 시퀀스 받아서 ecode 세팅
		int seq = mapper.getEventSeq();
		dto01.setEcode(String.valueOf(seq)); // 중요: 외부 시퀀스 사용
		int insertFlag = mapper.doSave(dto01);
		assertEquals(1, insertFlag);

		// 2. 단건 조회
		EventDTO dbData = mapper.doSelectOne(dto01);
		assertNotNull(dbData, "등록된 데이터가 조회되지 않음");

		// 3. 데이터 수정
		dbData.setTitle("수정된 제목");
		dbData.setContents("수정된 내용입니다.");
		dbData.setModId("modifier");

		// 4. 수정 실행
		int updateFlag = mapper.doUpdate(dbData);
		assertEquals(1, updateFlag, "업데이트 실패");

		// 5. 수정 결과 검증
		EventDTO updated = mapper.doSelectOne(dbData);
		assertEquals("수정된 제목", updated.getTitle());
		assertEquals("수정된 내용입니다.", updated.getContents());
		assertEquals("modifier", updated.getModId());

		log.debug(">> 수정 완료된 데이터: {}", updated);
	}

	@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);
		assertNotNull(dto01);

		log.debug("context: {}", context);
		log.debug("mapper: {}", mapper);
		log.debug("dto01: {}", dto01);
	}
}