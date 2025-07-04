package com.pcwk.ehr.event;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.mapper.EventMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class EventControllerTest {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Autowired
	EventMapper mapper;

	EventDTO dto01;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌────────────── setUp ──────────────┐");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		int seq = mapper.getEventSeq();
		dto01 = new EventDTO(String.valueOf(seq), "event@test.com", "이벤트제목", "이벤트내용", 0, new Date(), "admin01",
				new Date(), "admin01");

		log.debug("dto01: {}", dto01);
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("└───────────── tearDown ─────────────┘");
	}

	@Test
	void doRetrieve() throws Exception {
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		for (int i = 0; i < 6; i++) {
			mapper.saveAll();
		}
		assertEquals(600, mapper.getCount());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/event/doRetrieve.do")
				.param("pageNo", "1").param("pageSize", "10").param("div", "10").param("searchDiv", "10")
				.param("searchWord", "제목");

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());

		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		Map<String, Object> model = mvcResult.getModelAndView().getModel();

		int totalCnt = (int) model.get("totalCnt");
		log.debug("totalCnt: {}", totalCnt);

		List<EventDTO> list = (List<EventDTO>) model.get("list");
		list.forEach(vo -> log.debug("vo: {}", vo));

		assertEquals(10, list.size());
		assertEquals("event/event_list", mvcResult.getModelAndView().getViewName());
	}

	//@Disabled
	@Test
	void doSelectOne() throws Exception {
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 이벤트 등록
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 조회자(regId)를 다르게 설정 (본인 글이 아니어야 조회수 증가)
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/event/doSelectOne.do")
				.param("ecode", dto01.getEcode()).param("regId", dto01.getRegId() + "UP");

		// 요청 실행
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());

		// 결과 확인
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		Map<String, Object> model = mvcResult.getModelAndView().getModel();

		EventDTO outVO = (EventDTO) model.get("vo");
		log.debug("outVO: {}", outVO);

		// 기대값: 조회수 증가
		dto01.setReadCnt(dto01.getReadCnt() + 1);
		isSameEvent(outVO, dto01); // 비교 메서드 작성 필요

		// 뷰 이름 확인
		String viewName = mvcResult.getModelAndView().getViewName();
		assertEquals("event/event_mod", viewName);
	}

	private void isSameEvent(EventDTO expected, EventDTO actual) {
		assertEquals(expected.getEcode(), actual.getEcode());
		assertEquals(expected.getTitle(), actual.getTitle());
		assertEquals(expected.getContents(), actual.getContents());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getReadCnt(), actual.getReadCnt());
		assertEquals(expected.getModId(), actual.getModId());
	}

	//@Disabled
	@Test
	void doUpdate() throws Exception {
		// 1. 기존 데이터 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2. 데이터 등록
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 3. 단건 조회
		EventDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);

		String upString = "_U";

		// 4. 수정 요청 구성
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doUpdate.do")
				.param("ecode", outVO.getEcode()).param("email", outVO.getEmail())
				.param("title", outVO.getTitle() + upString).param("contents", outVO.getContents() + upString)
				.param("modId", outVO.getModId() + upString);

		// 5. 요청 실행 및 응답 검증
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

		// 6. 응답 본문 확인
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		// 7. 결과 검증
		assertEquals(1, resultMessage.getMessageId());
		assertEquals("회원님의 이벤트가 수정되었습니다.", resultMessage.getMessage());
	}

	//@Disabled
	@Test
	void doDelete() throws Exception {
		// 1. 기존 데이터 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2. 테스트용 데이터 등록
		String seq = String.valueOf(mapper.getEventSeq());
		dto01.setEcode(seq);
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 3. MockMvc로 삭제 요청
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doDelete.do").param("ecode",
				dto01.getEcode());

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

		// 4. 응답 확인
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		// 5. 결과 검증
		assertEquals(1, resultMessage.getMessageId());
		assertEquals("이벤트가 삭제 되었습니다.", resultMessage.getMessage());
		assertEquals(0, mapper.getCount());
	}

	//@Disabled
	@Test
	void doSave() throws Exception {
		log.debug("┌──────────── doSave() ─────────────┐");
		// 1. 기존 데이터 삭제
		mapper.deleteAll();

		// 2. 요청 구성
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doSave.do")
				.param("ecode", dto01.getEcode()).param("email", dto01.getEmail()).param("title", dto01.getTitle())
				.param("contents", dto01.getContents()).param("regId", dto01.getRegId())
				.param("modId", dto01.getModId());

		// 3. 요청 실행 및 검증
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		// 4. 결과 확인
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		log.debug("returnBody: {}", returnBody);

		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);
		log.debug("resultMessage: {}", resultMessage);

		// 5. 검증
		assertEquals(1, resultMessage.getMessageId());
		assertEquals("이벤트제목 글이 등록되었습니다.", resultMessage.getMessage());

		log.debug("└────────────────────────────────────┘");
	}

	//@Disabled
	@Test
	void beans() {
		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
		assertNotNull(mapper);

		log.debug("webApplicationContext: {}", webApplicationContext);
		log.debug("mockMvc: {}", mockMvc);
		log.debug("mapper: {}", mapper);
	}
}
