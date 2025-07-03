package com.pcwk.ehr.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

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

import com.pcwk.ehr.mapper.EventMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class EventServiceTest {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	EventService eventService;

	@Autowired
	EventMapper mapper;

	EventDTO dto01;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────── setUp() ───────┐");

		int seq = mapper.getEventSeq();
		dto01 = new EventDTO(String.valueOf(seq), // <-- 여기 수정
				"test@pcwk.com", "이벤트 제목", "이벤트 내용", 0, new Date(), "admin", new Date(), "admin");
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("└─────── tearDown() ───────┘");
	}

	@Test
	void doSelectOne() {
		// 1. 전체 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2. 등록
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 조회자 변경
		dto01.setRegId("otherUser");

		// 3. 서비스 호출
		EventDTO outVO = eventService.doSelectOne(dto01);
		assertNotNull(outVO);
		assertEquals(1, outVO.getReadCnt());
	}

	@Test
	void beans() {
		log.debug("context: {}", context);
		log.debug("eventService: {}", eventService);
		log.debug("eventMapper: {}", mapper);

		assertNotNull(context);
		assertNotNull(eventService);
		assertNotNull(mapper);
	}
}