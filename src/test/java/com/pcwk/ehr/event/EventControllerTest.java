package com.pcwk.ehr.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
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

		int seq = mapper.getEventSeq();
		dto01 = new EventDTO(seq, "event@carpick.com", "Carpick", "30", "신차 출시 이벤트", "운영자", 0, new Date(), "admin",
				new Date(), "admin");
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("테스트 종료");
	}

	//@Disabled
	@Test
	void doSelectOne() throws Exception {
		mapper.deleteAll();
		mapper.doSave(dto01);

		String viewerId = dto01.getRegId() + "_U";
		int expectedReadCnt = dto01.getReadCnt() + 1;

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/event/doSelectOne.do")
						.param("ecode", String.valueOf(dto01.getEcode())).param("regId", viewerId))
				.andExpect(status().isOk()).andDo(print()).andReturn();

		EventDTO outVO = (EventDTO) result.getModelAndView().getModel().get("vo");
		assertNotNull(outVO);
		assertEquals(expectedReadCnt, outVO.getReadCnt());
	}

	//@Disabled
	@Test
	void doUpdate() throws Exception {
		// 1. 기존 데이터 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2. 테스트용 데이터 등록
		int seq = mapper.getEventSeq();
		dto01.setEcode(seq); // int형으로 설정
		int saveFlag = mapper.doSave(dto01);
		assertEquals(1, saveFlag);

		// 3. 수정할 데이터 단건 조회
		EventDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);

		// 4. 수정 요청 구성 (loginUserId는 regId와 일치해야 함!)
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doUpdate.do")
			.param("ecode", String.valueOf(outVO.getEcode())) // 필수!
			.param("email", outVO.getEmail())
			.param("title", outVO.getTitle() + "_U")
			.param("div", outVO.getDiv())
			.param("contents", outVO.getContents() + "_U")
			.param("nickname", outVO.getNickname())
			.param("regId", outVO.getRegId()) // 꼭 같이 보내야 함
			.param("modId", "admin_U")
			.sessionAttr("loginUserId", outVO.getRegId()); // 작성자와 동일해야 권한 OK

		// 5. 요청 실행
		ResultActions resultActions = mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/plain;charset=UTF-8"));

		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		// 6. 검증
		assertEquals(1, resultMessage.getMessageId(), "수정 실패");
		assertEquals("이벤트가 수정되었습니다.", resultMessage.getMessage());
	}
	//@Disabled
	@Test
	void doDelete() throws Exception {
	    // 1. 기존 데이터 삭제
	    mapper.deleteAll();
	    assertEquals(0, mapper.getCount());

	    // 2. 테스트용 이벤트 데이터 등록
	    int seq = mapper.getEventSeq();
	    dto01.setEcode(seq); // ecode는 int 타입
	    int flag = mapper.doSave(dto01);
	    assertEquals(1, flag);

	    // 3. 등록된 데이터 다시 조회해서 정확한 ecode 확보
	    EventDTO dbData = mapper.doSelectOne(dto01);
	    assertNotNull(dbData);

	    // 4. 삭제 요청 구성 (세션 포함)
	    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doDelete.do")
	        .param("ecode", String.valueOf(dbData.getEcode())) // param은 문자열로 전달
	        .sessionAttr("loginUserId", "admin"); // ✅ 관리자 세션

	    // 5. 요청 실행 및 응답 파싱
	    ResultActions resultActions = mockMvc.perform(requestBuilder)
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("text/plain;charset=UTF-8"));

	    String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
	    MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

	    // 6. 검증
	    assertEquals(1, resultMessage.getMessageId(), "삭제 실패");
	    assertEquals("이벤트가 삭제되었습니다.", resultMessage.getMessage(), "삭제 메시지 불일치");
	    assertEquals(0, mapper.getCount(), "삭제 후 데이터가 남아 있음");
	}

	//@Disabled
	@Test
	void doSave() throws Exception {
		// 1. 기존 데이터 전체 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount(), "1. 초기 데이터 삭제 실패");

		// 2. 세션에 로그인 사용자(admin) 설정
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("loginUserId", "admin"); // 관리자 권한

		// 3. 요청 파라미터 구성 및 세션 포함
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/doSave.do")
				.param("email", dto01.getEmail()).param("title", dto01.getTitle()).param("div", dto01.getDiv())
				.param("contents", dto01.getContents()).param("nickname", dto01.getNickname())
				.param("regId", dto01.getRegId()).param("modId", dto01.getModId()).session(session); // ✅ 세션 주입

		// 4. 요청 실행 및 응답 검증
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"));

		// 5. 응답 메시지 파싱 및 검증
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();

		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		assertEquals(1, resultMessage.getMessageId(), "등록 실패: messageId가 1이 아님");
		assertEquals(dto01.getTitle() + "Carpick글이 등록되었습니다.", resultMessage.getMessage(), "등록 메시지 불일치");

		// 6. 실제 등록 확인
		assertEquals(1, mapper.getCount(), "등록 후 데이터 건수 불일치");
	}

	//@Disabled
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
