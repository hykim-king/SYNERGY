package com.pcwk.ehr.boardtest;

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

import com.pcwk.ehr.board.BoardDTO;
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
		log.debug("┌──────────── setUp() ────────────┐");

		int seq = mapper.getBoardSeq();

		dto01 = new BoardDTO(seq, "공지사항 제목", "내용입니다.", "admin01", "관리자", 0, new Date(), "admin01", new Date(),
				"admin01");

		search = new SearchDTO();
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("└──────────── tearDown() ────────────┘");
	}

	// @Disabled
	@Test
	void updateReadCnt() {
		mapper.deleteAll();

		int seq = mapper.getBoardSeq();
		dto01.setBoardCode(seq);
		dto01.setRegId("admin01"); // 저장 시 admin01

		mapper.doSave(dto01);

		// 다른 사용자로 세팅
		dto01.setRegId("otherUser");

		int flag = mapper.updateReadCnt(dto01); // ✅ 이제 1이 나와야 함
		assertEquals(1, flag);
	}

	// @Disabled
	@Test
	void doRetrieve() {
		mapper.deleteAll();
		int count = mapper.saveAll();
		assertEquals(100, count); // saveAll() INSERT LEVEL <= 100 기준

		search.setPageNo(1);
		search.setPageSize(10);
		search.setSearchDiv("10");
		search.setSearchWord("제목");

		List<BoardDTO> list = mapper.doRetrieve(search);
		assertNotNull(list);
		assertEquals(10, list.size());

		list.forEach(vo -> log.debug("vo={}", vo));
	}

	// @Disabled
	void addAndGet() {
		// 1. 전체 삭제
		mapper.deleteAll();

		// 2. 시퀀스 값 받아서 DTO에 지정
		int seq = mapper.getBoardSeq();
		dto01.setBoardCode(seq); // ★ 반드시 필요!

		// 3. 저장
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 4. 단건 조회
		BoardDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);

		// 5. 동일성 검증
		isSameBoard(outVO, dto01);
	}

	// @Disabled
	@Test
	void doDelete() {
		mapper.deleteAll();
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		flag = mapper.doDelete(dto01);
		assertEquals(1, flag);

		assertEquals(0, mapper.getCount());
	}

	// @Disabled
	@Test
	void doUpdate() {
		mapper.deleteAll();

		int seq = mapper.getBoardSeq();
		dto01.setBoardCode(seq);

		int saveFlag = mapper.doSave(dto01);
		assertEquals(1, saveFlag);

		BoardDTO dbData = mapper.doSelectOne(dto01);
		assertNotNull(dbData);
		log.debug("조회된 데이터 board_code: {}", dbData.getBoardCode());

		dbData.setTitle("오징어");
		dbData.setContents("안녕하세요.");
		dbData.setModId("MOnsteER");

		int updateFlag = mapper.doUpdate(dbData);
		assertEquals(1, updateFlag); // 
		BoardDTO updated = mapper.doSelectOne(dbData);
		assertEquals("오징어", updated.getTitle());
	}

	private void isSameBoard(BoardDTO actual, BoardDTO expected) {
		assertEquals(expected.getBoardCode(), actual.getBoardCode());
		assertEquals(expected.getTitle(), actual.getTitle());
		assertEquals(expected.getContents(), actual.getContents());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getNickname(), actual.getNickname());
		assertEquals(expected.getReadCnt(), actual.getReadCnt());
		assertEquals(expected.getRegId(), actual.getRegId());
		assertEquals(expected.getModId(), actual.getModId());
	}

	// @Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);
		assertNotNull(dto01);

		log.debug("1. context:{}", context);
		log.debug("1. mapper:{}", mapper);
		log.debug("1. dto01:{}", dto01);

	}

}