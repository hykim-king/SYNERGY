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

		dto01 = new EventDTO(seq, // int ecode
				"test@pcwk.com", // email
				"이벤트 제목", // title
				"20", // div
				"이벤트 내용입니다.", // contents
				"테스터", // nickname
				0, // readCnt
				new Date(), // regDt
				"admin", // regId
				new Date(), // modDt
				"admin" // modId
		);

		search = new SearchDTO();
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("└──────────── tearDown() ────────────┘");
	}

	//@Disabled
	@Test
	void updateReadCnt() {
		mapper.deleteAll();

		int seq = mapper.getEventSeq();
		dto01.setEcode(seq);
		dto01.setRegId("admin");

		mapper.doSave(dto01);

		EventDTO readParam = new EventDTO();
		readParam.setEcode(dto01.getEcode());
		readParam.setRegId("otherUser"); // 작성자가 아닌 사용자로 조회수 증가

		int flag = mapper.updateReadCnt(readParam);
		assertEquals(1, flag);
	}

	//@Disabled
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

	//@Disabled
	@Test
	void doDelete() {
		mapper.deleteAll();

		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		flag = mapper.doDelete(dto01);
		assertEquals(1, flag);

		assertEquals(0, mapper.getCount());
	}

	//@Disabled
	@Test
	void doUpdate() {
		mapper.deleteAll();

		int seq = mapper.getEventSeq();
		dto01.setEcode(seq);

		int insertFlag = mapper.doSave(dto01);
		assertEquals(1, insertFlag);

		EventDTO dbData = mapper.doSelectOne(dto01);
		assertNotNull(dbData, "등록된 데이터가 조회되지 않음");

		dbData.setTitle("수정된 제목");
		dbData.setContents("수정된 내용입니다.");
		dbData.setModId("modifier");

		int updateFlag = mapper.doUpdate(dbData);
		assertEquals(1, updateFlag, "업데이트 실패");

		EventDTO updated = mapper.doSelectOne(dbData);
		assertEquals("수정된 제목", updated.getTitle());
		assertEquals("수정된 내용입니다.", updated.getContents());
		assertEquals("modifier", updated.getModId());

		log.debug(">> 수정 완료된 데이터: {}", updated);
	}

	//@Disabled
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