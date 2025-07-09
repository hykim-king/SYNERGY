package com.pcwk.ehr.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.mapper.BoardMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class BoardControllerTest {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Autowired
	BoardMapper mapper;

	BoardDTO dto01;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		int seq = mapper.getBoardSeq();
		dto01 = new BoardDTO(seq, "Carpick", "10", "내용입니다", "admin01", "관리자", 0, new Date(), "admin01", new Date(),
				"admin01");

		log.debug("dto01: {}", dto01);
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("테스트 종료");
	}

	//@Disabled
	@Test
	void doRetrieve() throws Exception {
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		int count = mapper.saveAll();
		assertEquals(100, count);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/doRetrieve.do")
				.param("pageNo", "1").param("pageSize", "10").param("div", "10").param("searchDiv", "10")
				.param("searchWord", "제목1");

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());

		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		Map<String, Object> model = mvcResult.getModelAndView().getModel();

		int totalCnt = (int) model.get("totalCnt");
		log.debug("totalCnt: {}", totalCnt);

		List<BoardDTO> list = (List<BoardDTO>) model.get("list");
		list.forEach(vo -> log.debug("vo: {}", vo));

		assertEquals(6, list.size());
		assertEquals("board/board_list", mvcResult.getModelAndView().getViewName());
	}

	@Disabled
	@Test
	void doSelectOne() throws Exception {
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/doSelectOne.do")
				.param("boardCode", String.valueOf(dto01.getBoardCode())).param("regId", dto01.getRegId() + "UP");

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());

		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		Map<String, Object> model = mvcResult.getModelAndView().getModel();

		BoardDTO outVO = (BoardDTO) model.get("vo");
		log.debug("outVO: {}", outVO);

		dto01.setReadCnt(dto01.getReadCnt() + 1);
		isSameBoard(outVO, dto01);

		String viewName = mvcResult.getModelAndView().getViewName();
		assertEquals("board/board_mod", viewName);
	}

	@Disabled
	@Test
	void doUpdate() throws Exception {
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		BoardDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);

		String upString = "_U";

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doUpdate.do")
				.param("boardCode", String.valueOf(outVO.getBoardCode())).param("title", outVO.getTitle() + upString)
				.param("div", outVO.getDiv()).param("contents", outVO.getContents() + upString)
				.param("modId", outVO.getModId() + upString);

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals("회원님의 글이 수정되었습니다.", resultMessage.getMessage());
	}

	@Disabled
	@Test
	void doDelete() throws Exception {
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		int seq = mapper.getBoardSeq();
		dto01.setBoardCode(seq);
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doDelete.do")
				.param("boardCode", String.valueOf(dto01.getBoardCode()));

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals("게시판 글이 삭제 되었습니다.", resultMessage.getMessage());
		assertEquals(0, mapper.getCount());
	}

	@Disabled
	@Test
	void doSave() throws Exception {
		mapper.deleteAll();

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doSave.do")
				.param("title", dto01.getTitle()).param("div", dto01.getDiv()).param("contents", dto01.getContents())
				.param("id", dto01.getId()).param("nickname", dto01.getNickname()).param("regId", dto01.getRegId())
				.param("modId", dto01.getModId());

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals("Carpick 글이 등록되었습니다.", resultMessage.getMessage());
	}

	@Disabled
	@Test
	void beans() {
		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
		assertNotNull(mapper);
	}

	private void isSameBoard(BoardDTO actual, BoardDTO expected) {
		assertEquals(expected.getBoardCode(), actual.getBoardCode());
		assertEquals(expected.getTitle(), actual.getTitle());
		assertEquals(expected.getDiv(), actual.getDiv());
		assertEquals(expected.getContents(), actual.getContents());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getNickname(), actual.getNickname());
		assertEquals(expected.getReadCnt(), actual.getReadCnt());
		assertEquals(expected.getRegId(), actual.getRegId());
		assertEquals(expected.getModId(), actual.getModId());
	}
}