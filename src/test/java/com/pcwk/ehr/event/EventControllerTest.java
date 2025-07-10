package com.pcwk.ehr.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import com.pcwk.ehr.mapper.EventMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class EventControllerTest {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Autowired
	EventMapper mapper;

	EventDTO dto01;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		int seq = mapper.getEventSeq(); // 시퀀스 조회
		dto01 = new EventDTO(String.valueOf(seq), // ecode
				"event@carpick.com", // email
				"Carpick", // title
				"30", // div
				"신차 출시 이벤트", // contents
				"운영자", // nickname
				0, // readCnt
				new Date(), // regDt
				"admin", // regId
				new Date(), // modDt
				"admin" // modId
		);

		log.debug("dto01: {}", dto01);
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("테스트 종료");
	}

	//@Disabled
	@Test
	void doRetrieve() throws Exception {
		log.debug("┌───── EventController doRetrieve() 테스트 시작 ─────┐");

		// 1. 기존 데이터 전체 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount(), "1. 초기 데이터 삭제 실패");

		// 2. 테스트 데이터 대량 등록 (600건)
		for (int i = 0; i < 6; i++) {
			int inserted = mapper.saveAll();
			assertEquals(100, inserted, (i + 1) + "번째 saveAll 실패");
		}

		int totalInserted = mapper.getCount();
		assertEquals(600, totalInserted, "2. 전체 등록된 데이터 수 오류");

		// 3. 요청 구성
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/event/doRetrieve.do")
				.param("pageNo", "1").param("pageSize", "10").param("div", "10").param("searchDiv", "10") // 제목 검색
				.param("searchWord", "제목");

		// 4. 요청 실행 및 응답 확인
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());

		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		Map<String, Object> model = mvcResult.getModelAndView().getModel();

		// 5. totalCnt 검증
		int totalCnt = (int) model.get("totalCnt");
		log.debug("▶ totalCnt: {}", totalCnt);
		assertTrue(totalCnt >= 10, "3. 총 건수는 10건 이상이어야 합니다.");

		// 6. 리스트 검증
		List<EventDTO> list = (List<EventDTO>) model.get("list");
		assertNotNull(list, "4. 조회 결과 리스트가 null입니다.");
		assertEquals(10, list.size(), "5. 조회된 리스트 사이즈가 10이 아닙니다.");

		list.forEach(vo -> log.debug("vo: {}", vo));

		// 7. 뷰 이름 검증
		String viewName = mvcResult.getModelAndView().getViewName();
		assertEquals("event/event_list", viewName, "6. 반환된 뷰 이름이 일치하지 않습니다.");

		log.debug("└───── EventController doRetrieve() 테스트 종료 ─────┘");
	}

	@Disabled
	@Test
	void doSelectOne() throws Exception {
		log.debug("┌──────────────── doSelectOne() ────────────────┐");

		// 1. 기존 데이터 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount(), "① 데이터가 초기화되지 않음");

		// 2. 테스트용 이벤트 등록
		String seq = String.valueOf(mapper.getEventSeq());
		dto01.setEcode(seq);
		int saveFlag = mapper.doSave(dto01);
		assertEquals(1, saveFlag, "② 이벤트 등록 실패");

		// 3. 본인 이외의 사용자 ID (조회수 증가 유도)
		String viewerId = dto01.getRegId() + "_U";
		int expectedReadCnt = dto01.getReadCnt() + 1;

		// 4. MockMvc 요청 구성
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/event/doSelectOne.do")
				.param("ecode", dto01.getEcode()).param("regId", viewerId); // 조회자 ID (작성자와 다르게)

		// 5. 요청 실행 및 응답 결과 추출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());

		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		Map<String, Object> model = mvcResult.getModelAndView().getModel();

		EventDTO outVO = (EventDTO) model.get("vo");
		log.debug("▶ 조회된 이벤트: {}", outVO);

		// 6. 뷰 이름 검증
		String viewName = mvcResult.getModelAndView().getViewName();
		assertEquals("event/event_mod", viewName, "③ 뷰 이름이 일치하지 않음");

		// 7. 조회수 증가 검증 (작성자 외의 사용자일 경우)
		assertEquals(expectedReadCnt, outVO.getReadCnt(), "④ 조회수가 1 증가해야 함");

		// 8. 나머지 필드 검증
		assertEquals(dto01.getEcode(), outVO.getEcode(), "⑤ 이벤트 코드 불일치");
		assertEquals(dto01.getEmail(), outVO.getEmail(), "⑥ 이메일 불일치");
		assertEquals(dto01.getTitle(), outVO.getTitle(), "⑦ 제목 불일치");
		assertEquals(dto01.getContents(), outVO.getContents(), "⑧ 내용 불일치");
		assertEquals(dto01.getNickname(), outVO.getNickname(), "⑨ 닉네임 불일치");

		log.debug("└───────────────────────────────────────────────┘");
	}

	@Disabled
	@Test
	void doUpdate() throws Exception {
		// 1. 기존 데이터 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2. 테스트용 데이터 등록
		int seq = mapper.getEventSeq();
		dto01.setEcode(String.valueOf(seq));
		int saveFlag = mapper.doSave(dto01);
		assertEquals(1, saveFlag);

		// 3. 등록된 데이터 단건 조회
		EventDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO, "등록된 이벤트 조회 실패");

		String upString = "_U"; // 수정 문자열

		// 4. 수정 요청 구성
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doUpdate.do")
				.param("ecode", outVO.getEcode())
			    .param("email", outVO.getEmail()) // ✅ 이 줄 추가!
			    .param("title", outVO.getTitle() + "_U")
			    .param("div", outVO.getDiv())
			    .param("contents", outVO.getContents() + "_U")
			    .param("nickname", outVO.getNickname())
			    .param("modId", outVO.getModId() + "_U");

		// 5. 요청 실행 및 응답 검증
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

		// 6. 응답 메시지 파싱
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		// 7. 결과 검증
		assertEquals(1, resultMessage.getMessageId());
		assertEquals("회원님의 이벤트가 수정되었습니다.", resultMessage.getMessage());
	}

	@Disabled
	@Test
	void doDelete() throws Exception {
		// 1. 기존 데이터 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2. 테스트용 이벤트 데이터 등록
		int seq = mapper.getEventSeq();
		dto01.setEcode(String.valueOf(seq));
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 3. 삭제 요청 구성
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doDelete.do").param("ecode",
				dto01.getEcode());

		// 4. 요청 실행 및 응답 검증
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

		// 5. 응답 본문(JSON 문자열) → MessageDTO 파싱
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		// 6. 결과 검증
		assertEquals(1, resultMessage.getMessageId());
		assertEquals("이벤트가 삭제 되었습니다.", resultMessage.getMessage());
		assertEquals(0, mapper.getCount());
	}

	@Disabled
	@Test
	void doSave() throws Exception {
		mapper.deleteAll();

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doSave.do")
				.param("email", dto01.getEmail()).param("title", dto01.getTitle()).param("div", dto01.getDiv())
				.param("contents", dto01.getContents()).param("nickname", dto01.getNickname())
				.param("regId", dto01.getRegId()).param("modId", dto01.getModId());

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals(dto01.getTitle() + " 글이 등록되었습니다.", resultMessage.getMessage());
	}

	@Disabled
	@Test
	void beans() {
		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
		assertNotNull(mapper);
	}

	private void isSameEvent(EventDTO actual, EventDTO expected) {
		assertEquals(expected.getEcode(), actual.getEcode(), "이벤트 코드 불일치");
		assertEquals(expected.getTitle(), actual.getTitle(), "제목 불일치");
		assertEquals(expected.getDiv(), actual.getDiv(), "게시 구분 불일치");
		assertEquals(expected.getContents(), actual.getContents(), "내용 불일치");
		assertEquals(expected.getEmail(), actual.getEmail(), "이메일 불일치");
		assertEquals(expected.getNickname(), actual.getNickname(), "닉네임 불일치");
		assertEquals(expected.getReadCnt(), actual.getReadCnt(), "조회수 불일치");
		assertEquals(expected.getRegId(), actual.getRegId(), "등록자 ID 불일치");
		assertEquals(expected.getModId(), actual.getModId(), "수정자 ID 불일치");
	}

}
