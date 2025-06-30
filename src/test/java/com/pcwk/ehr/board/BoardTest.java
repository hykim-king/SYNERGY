package com.pcwk.ehr.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

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

import com.pcwk.ehr.mapper.BoardMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class BoardTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardMapper mapper;


	@Autowired
	DataSource dataSource;

	List<BoardDTO> boards;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌────────────────────────────────────────────┐");
		log.debug("│ setUp() 실행                                │");
		log.debug("└────────────────────────────────────────────┘");

		mapper.deleteAll();

		boards = Arrays.asList(new BoardDTO(1, "왜", "안돼", "그만", "쉬자", 0, new Date(), "admin", new Date(), "admin"),
				new BoardDTO(2, "무엇일까", "내용2", "user02", "작성자2", 0, new Date(), "admin", new Date(), "admin"),
				new BoardDTO(3, "제목3", "내용3", "user03", "작성자3", 0, new Date(), "admin", new Date(), "admin"));

		for (BoardDTO dto : boards) {
			boardService.doSave(dto);
		}
	}

	@Disabled
	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ tearDown() 실행                             │");
		log.debug("└───────────────────────────┘");
		mapper.deleteAll();
	}

	// @Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(boardService);

		log.debug("context : {}", context);
		log.debug("boardService : {}", boardService);

	}

	@Disabled
	@Test
	void doSave() {
		BoardDTO dto = new BoardDTO(10, "새글", "새 내용", "newUser", "새작성자", 0, new Date(), "admin", new Date(), "admin");

		int flag = boardService.doSave(dto);
		assertEquals(1, flag);

		BoardDTO result = mapper.doSelectOne(dto.getBoardCode());
		assertEquals("새글", result.getTitle());
		assertEquals("새작성자", result.getNickname());
	}

	@Disabled
	@Test
	void doUpdate() {
		BoardDTO dto = boards.get(0);
		dto.setTitle("수정된 제목");
		dto.setContents("수정된 내용");

		int flag = boardService.doUpdate(dto);
		assertEquals(1, flag);

		BoardDTO updated = mapper.doSelectOne(dto.getBoardCode());
		assertEquals("수정된 제목", updated.getTitle());
		assertEquals("수정된 내용", updated.getContents());
	}

//	@Disabled
//	@Test
//	void doRetrieve() {
//		BoardMapperDTO search = new BoardMapperDTO(1, 10, "title", "제목2");
//
//		List<BoardDTO> result = boardService.doRetrieve(search);
//		assertEquals(1, result.size());
//		assertEquals("제목2", result.get(0).getTitle());
//	}

	@Disabled
	@Test
	void doDelete() {
		BoardDTO dto = boards.get(0);
		int flag = boardService.doDelete(dto);
		assertEquals(1, flag);
		assertEquals(2, mapper.getCount());
	}
}