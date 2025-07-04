package com.pcwk.ehr.repair;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class RepairResControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    // ✅ 1. 리페어 신청 등록 성공 테스트
    @Test
    void testApplyRepairSuccess() throws Exception {
        mockMvc.perform(
                post("/repair/apply.do")
                        .param("id", "testuser01")
                        .param("name", "홍길동")
                        .param("phone", "010-1234-5678")
                        .param("carCode", "67")
                        .param("retailerCode", "20001")
                        .param("repairDate", "2025-07-01")
                        .param("repairDesc", "브레이크 점검"))
                .andExpect(status().isOk())
                .andExpect(view().name("repair/repairResult"))
                .andExpect(model().attributeExists("dto"))
                .andExpect(model().attribute("success", true));
    }

    // ✅ 2. 리페어 목록 조회
    @Disabled
    @Test
    void testRepairList() throws Exception {
        mockMvc.perform(get("/repair/list.do"))
                .andExpect(status().isOk())
                .andExpect(view().name("repair/repairList"))
                .andExpect(model().attributeExists("repairList"));
    }

    // ✅ 3. 리페어 상세 조회
    @Disabled
    @Test
    void testRepairDetail() throws Exception {
        mockMvc.perform(get("/repair/detail.do")
                        .param("repairNo", "46")) // 존재하는 번호로 테스트
                .andExpect(status().isOk())
                .andExpect(view().name("repair/repairDetail"))
                .andExpect(model().attributeExists("repair"));
    }
    
    @Disabled
    @Test
    void testRepairUpdate() throws Exception {
        mockMvc.perform(post("/repair/update.do") // 이 경로는 컨트롤러에 맞게 조정 가능
                .param("repairNo", "1")
                .param("name", "수정된이름")
                .param("phone", "010-8888-7777")
                .param("carCode", "67")
                .param("retailerCode", "20001")
                .param("repairDate", "2025-07-05")
                .param("repairDesc", "수정된 점검 내용")
                .param("modId", "admin"))
            .andExpect(status().isOk())
            .andExpect(view().name("repair/repairResult")) // 혹은 업데이트 후 이동할 뷰
            .andExpect(model().attributeExists("dto"))
            .andExpect(model().attribute("success", true));
    }

    @Disabled
    @Test
    void testRepairDelete() throws Exception {
        mockMvc.perform(get("/repair/delete.do") // 이 경로도 너 컨트롤러에 맞춰야 함
                .param("repairNo", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("repair/repairResult")) // 삭제 결과 페이지 뷰
            .andExpect(model().attribute("success", true));
    }
    
}