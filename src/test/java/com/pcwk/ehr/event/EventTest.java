package com.pcwk.ehr.event;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

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

import com.pcwk.ehr.eventService.EventService;
import com.pcwk.ehr.mapper.EventMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class EventTest {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	EventService eventService;

	@Autowired
	EventMapper mapper;

	EventDTO event;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("======== setUp() ========");
		String uuid = UUID.randomUUID().toString().substring(0, 10);
		event = new EventDTO();
		event.setEcode(uuid);
		event.setEmail("orange@domain.com");
		event.setTitle("이벤트 검색");
		event.setContents("언제 당첨 될까");
		event.setRegId("orange");
		event.setModId("admin");

		mapper.doInsert(event);
		log.info(">>> 테스트용: {}", event);
	}

	@AfterEach
	void tearDown() throws Exception {
		if (event != null) {
			mapper.doDelete(event);
			log.info(">>> 이벤트 삭제 완료: {}", event.getEcode());
		}
		log.debug("======== tearDown() ========");
	}

	/**
	 * 스프링 컨텍스트 및 빈 주입 테스트
	 */
	//@Disabled
	@Test
	public void contextLoads() {
		assertNotNull(context);
		assertNotNull(mapper);
		assertNotNull(eventService);
		log.info(">>> 컨텍스트 넣어서 확인 완료");
	}

	/**
	 * 이벤트 등록 테스트
	 */
	//@Disabled
	@Test
	public void doInsert() {
		EventDTO newEvent = new EventDTO();
		newEvent.setEcode(UUID.randomUUID().toString().substring(0, 10));
		newEvent.setEmail("shell@domain.com");
		newEvent.setTitle("들어가라");
		newEvent.setContents("추가하세요");
		newEvent.setRegId("user");
		newEvent.setModId("itsme");

		mapper.doInsert(newEvent);
		log.info(">>> 이벤트 등록 완료: {}", newEvent);

		mapper.doDelete(newEvent); // 정리
	}

	/**
	 * 전체 이벤트 조회 테스트
	 */
	//@Disabled
	@Test
	public void selectAll() {
		List<EventDTO> list = mapper.selectAll();
		assertTrue(list.size() >= 0);
		for (EventDTO dto : list) {
			log.info("이벤트: {}", dto);
		}
	}

	/**
	 * 단건 이벤트 조회 테스트
	 */
	//@Disabled
	@Test
	public void selectOne() {
		EventDTO result = mapper.selectOne(event.getEcode());
		if (result != null) {
			log.info("단건 조회 성공: {}", result);
		} else {
			log.warn("해당 이벤트 없음. ecode: {}", event.getEcode());
		}
	}

	/**
	 * 이벤트 삭제 테스트
	 */
	//@Disabled
	@Test
	public void doDelete() {
		EventDTO temp = new EventDTO();
		temp.setEcode(UUID.randomUUID().toString().substring(0, 10));
		temp.setEmail("shell@domain.com");
		temp.setTitle("지워보자");
		temp.setContents("삭제용 내용");
		temp.setRegId("user");
		temp.setModId("itsme");

		mapper.doInsert(temp);
		mapper.doDelete(temp);
		log.info(">>> 이벤트 삭제 완료: {}", temp.getEcode());
	}

	/**
	 * 주요 빈 주입 확인 테스트
	 */
	//@Disabled
	@Test
	void beans() {
		assertNotNull(mapper);
		assertNotNull(context);
		assertNotNull(eventService);

		log.debug("context      : {}", context);
		log.debug("mapper       : {}", mapper);
		log.debug("eventService : {}", eventService);
	}
}