package com.pcwk.ehr.boardtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.mapper.BoardMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class BoardControllerTest {

    Logger log = LogManager.getLogger(getClass());

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Autowired
    BoardMapper mapper;

    BoardDTO dto01;

    @BeforeEach
    void setUp() throws Exception {
        log.debug("┌────────────── setUp ──────────────┐");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        int seq = mapper.getBoardSeq();
        dto01 = new BoardDTO(seq, "Carpick", "내용입니다", "admin01", "관리자", 0, new Date(), "admin01", new Date(), "admin01");

        log.debug("dto01: {}", dto01);
    }

    @AfterEach
    void tearDown() throws Exception {
        log.debug("└───────────── tearDown ─────────────┘");
    }
    //@Disabled
    @Test
    void doSave() throws Exception {
        log.debug("┌──────────── doSave() ─────────────┐");

        // 1. 기존 데이터 전체 삭제
        mapper.deleteAll();

        // 2. Mock 요청 구성
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doSave.do")
                .param("title", dto01.getTitle())
                .param("contents", dto01.getContents())
                .param("id", dto01.getId())
                .param("nickname", dto01.getNickname())
                .param("regId", dto01.getRegId())
                .param("modId", dto01.getModId());

        // 3. 실행 및 응답 검증
        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

        // 4. 응답 문자열 확인
        String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
        log.debug("returnBody: {}", returnBody);

        // 5. JSON → MessageDTO
        MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);
        log.debug("resultMessage: {}", resultMessage);

        // 6. 검증
        assertEquals(1, resultMessage.getMessageId());
        assertEquals("Carpick 글이 등록되었습니다.", resultMessage.getMessage());

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