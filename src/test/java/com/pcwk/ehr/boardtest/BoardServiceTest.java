package com.pcwk.ehr.boardtest;

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

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.board.BoardService;
import com.pcwk.ehr.mapper.BoardMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class BoardServiceTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	BoardService boardService;

	@Autowired
	BoardMapper mapper;

	BoardDTO dto01;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌──────────────────────────────┐");
		log.debug("│ setUp()                      │");
		log.debug("└──────────────────────────────┘");

		int seq = mapper.getBoardSeq();
		dto01 = new BoardDTO(seq, "공지사항 제목", "내용입니다.", "admin01", "관리자", 0, new Date(), "admin01", new Date(),
				"admin01");

	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌───────────────────────────────┐");
		log.debug("│ tearDown()                    │");
		log.debug("└───────────────────────────────┘");

	}

	@Test
	void doSelectOne() {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1.전체삭제
		// 2. 단건 등록
		// 3. doSelectOne() Service호출

		// 1.
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2.
		log.debug("before:{}", dto01);
		int flag = mapper.doSave(dto01);
		log.debug("after:{}", dto01);
		assertEquals(1, flag);

		// 등록자 수정
		dto01.setRegId("james");
		// 3.
		BoardDTO outVO = boardService.doSelectOne(dto01);

		assertNotNull(outVO);
		assertEquals(1, outVO.getReadCnt());

	}

	@Test
	void beans() {
		log.debug("┌───────────────────────────────┐");
		log.debug("│ beans()                       │");
		log.debug("└───────────────────────────────┘");
		assertNotNull(context);
		assertNotNull(boardService);
		assertNotNull(mapper);

		log.debug("context:{}" + context);
		log.debug("boardService:{}" + boardService);
		log.debug("mapper:{}" + mapper);
	}

}
