package com.pcwk.ehr.board;

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
import com.pcwk.ehr.mapper.BoardMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class BoardTest {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	BoardMapper mapper;

	BoardDTO dto01;
	SearchDTO search;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌──── setUp() ────┐");

		int seq = mapper.getBoardSeq();

		dto01 = new BoardDTO(seq, "공지사항 제목", "10", "내용입니다.", "admin01", "관리자", 0, new Date(), "admin01", new Date(),
				"admin01");

		search = new SearchDTO();
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("└──── tearDown() ────┘");
	}
	@Disabled
	@Test
	void updateReadCnt() {
		mapper.deleteAll();

		int seq = mapper.getBoardSeq();
		dto01.setBoardCode(seq);
		dto01.setRegId("admin01");

		mapper.doSave(dto01);

		// 다른 사용자로 조회할 경우 조회수 증가
		dto01.setRegId("otherUser");

		int flag = mapper.updateReadCnt(dto01);
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
		search.setSearchDiv("10"); // title 검색
		search.setSearchWord("제목");
		search.setDiv("10");

		List<BoardDTO> list = mapper.doRetrieve(search);
		assertNotNull(list);
		assertEquals(10, list.size());

		list.forEach(vo -> log.debug("vo={}", vo));
	}
	@Disabled
	@Test
	void addAndGet() {
		mapper.deleteAll();

		int seq = mapper.getBoardSeq();
		dto01.setBoardCode(seq);

		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		BoardDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);

		isSameBoard(outVO, dto01);
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

	    int flag = mapper.doSave(dto01);
	    assertEquals(1, flag);

	    BoardDTO dbData = mapper.doSelectOne(dto01);
	    assertNotNull(dbData); // 여기서 null 안 나옴

	    dbData.setTitle("오징어");
	    dbData.setContents("안녕하세요.");
	    dbData.setDiv("20");
	    dbData.setModId("monster");

	    flag = mapper.doUpdate(dbData);
	    assertEquals(1, flag);

	    BoardDTO updated = mapper.doSelectOne(dbData);
	    assertEquals("오징어", updated.getTitle());
	    assertEquals("20", updated.getDiv());
	}

	private void isSameBoard(BoardDTO actual, BoardDTO expected) {
	    assertEquals(expected.getBoardCode(), actual.getBoardCode(), "boardCode 불일치");
	    assertEquals(expected.getTitle(), actual.getTitle(), "title 불일치");
	    assertEquals(expected.getDiv(), actual.getDiv(), "div 불일치");
	    assertEquals(expected.getContents(), actual.getContents(), "contents 불일치");
	    assertEquals(expected.getId(), actual.getId(), "id 불일치");
	    assertEquals(expected.getNickname(), actual.getNickname(), "nickname 불일치");
	    assertEquals(expected.getReadCnt(), actual.getReadCnt(), "readCnt 불일치");
	    assertEquals(expected.getRegId(), actual.getRegId(), "regId 불일치");
	    assertEquals(expected.getModId(), actual.getModId(), "modId 불일치");
	}
	@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);
		assertNotNull(dto01);

		log.debug("1. context: {}", context);
		log.debug("2. mapper: {}", mapper);
		log.debug("3. dto01: {}", dto01);
	}
}