package com.pcwk.ehr.boardtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
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

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.board.BoardService;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.BoardMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class BoardTest {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	BoardMapper boardMapper;

	@Autowired
	BoardService boardService;

	BoardDTO board01;

	@BeforeEach
	void setUp() throws SQLException {
		board01 = new BoardDTO();
		board01.setTitle("CARPICK");
		board01.setContents("dddd");
		board01.setId("dd");
		board01.setNickname("asas");
		board01.setRegId("asdgs");
		board01.setModId("admin");

		boardMapper.deleteAll(); // 데이터 초기화
	}

	@AfterEach
	void tearDown() {
		log.debug("=== @AfterEach ===");
	}

	@Test
	void doSave() {
		board01.setBoardCode(0); // selectKey가 자동으로 설정해줌
		int flag = boardMapper.doSave(board01);
		assertEquals(1, flag, "등록 성공 여부");

		List<BoardDTO> list = boardMapper.getAll();
		assertTrue(list.size() > 0);
	}

	@Test
	void doRetrieve() {
		for (int i = 1; i <= 3; i++) {
			BoardDTO dto = new BoardDTO();
			dto.setTitle("검색테스트" + i);
			dto.setContents("내용" + i);
			dto.setId("user" + i);
			dto.setNickname("닉네임" + i);
			dto.setRegId("admin");
			dto.setModId("admin");
			boardMapper.doSave(dto);
		}

		SearchDTO search = new SearchDTO();
		search.setSearchDiv("10"); // 제목 기준
		search.setSearchWord("검색테스트");
		search.setPageNo(1);
		search.setPageSize(10);

		List<BoardDTO> result = boardMapper.doRetrieve(search);
		assertTrue(result.size() >= 3, "검색 결과 수 확인");

		for (BoardDTO dto : result) {
			log.debug(dto);
		}
	}

	@Test
	void doUpdate() {
		// 1. 저장
		boardMapper.doSave(board01);

		// 2. 저장된 boardCode 가져오기
		List<BoardDTO> list = boardMapper.getAll();
		assertTrue(list.size() > 0, "게시글이 존재해야 합니다");

		BoardDTO saved = list.get(0); // 안전하게 첫 번째 항목
		log.debug("Saved boardCode: {}", saved.getBoardCode());

		// 3. 수정
		saved.setTitle("수정된 제목");
		saved.setContents("수정된 내용");
		saved.setModId("수정자");

		// 4. update 호출
		int flag = boardMapper.doUpdate(saved);
		assertEquals(1, flag, "수정 성공 여부");

		// 5. 수정 결과 확인
		BoardDTO updated = boardMapper.doSelectOne(saved);
		assertEquals("수정된 제목", updated.getTitle());
		assertEquals("수정된 내용", updated.getContents());
	}

	@Test
	void beans() {
		assertNotNull(boardMapper);
		assertNotNull(context);
		assertNotNull(boardService);

		log.debug("context    : {}", context);
		log.debug("userService: {}", boardService);
		log.debug("boardMapper: {}", boardMapper);
	}
}