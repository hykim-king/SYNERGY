package com.pcwk.ehr.retailer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { 
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class RetailerControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(context).build();
    }

    // ✅ 1. 전체 리테일러 목록(페이징) 조회 테스트
    @Test
    void testListWithPaging() throws Exception {
        mockMvc.perform(get("/retailer/list.do")
                .param("pageNum", "1")
                .param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(view().name("retailer/list"))
            .andExpect(model().attributeExists("retailerList"))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attributeExists("pageSize"))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attributeExists("totalCount"));
    }
    // ✅ 2. 전체 리테일러 목록(페이징X) 조회 테스트
    @Test
    void testAllList() throws Exception {
    	mockMvc.perform(get("/retailer/all.do"))
            .andExpect(status().isOk())
            .andExpect(view().name("retailer/all"))
            .andExpect(model().attributeExists("retailerList"));
    }

    // ✅ 3. 리테일러 상세 정보 조회 테스트
    @Test
    void testDetail() throws Exception {
        // 실제 존재하는 코드 중 하나를 사용하세요!
        mockMvc.perform(get("/retailer/detail/{retailerId}.do", 313))
            .andExpect(status().isOk())
            .andExpect(view().name("retailer/detail"))
            .andExpect(model().attributeExists("retailer"));
    }

}
