package com.pcwk.ehr.car;

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
public class CarControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(context).build();
    }

    // ✅ 1. 전체 자동차 리스트 조회 테스트
    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/car/list.do"))
                .andExpect(status().isOk())
                .andExpect(view().name("car/list"))
                .andExpect(model().attributeExists("carList"));
    }

    // ✅ 2. 브랜드별 자동차 리스트 조회 테스트
    @Test
    void testListByBrand() throws Exception {
        mockMvc.perform(get("/car/brand.do").param("brand", "현대"))
                .andExpect(status().isOk())
                .andExpect(view().name("car/list"))
                .andExpect(model().attributeExists("carList"))
                .andExpect(model().attributeExists("selectedBrand"));
    }

    // ✅ 3. 자동차 상세 조회 테스트
    @Test
    void testDetail() throws Exception {
        mockMvc.perform(get("/car/detail/{carCode}.do", "64")) // 실제 존재하는 carCode 필요!
                .andExpect(status().isOk())
                .andExpect(view().name("car/detail"))
                .andExpect(model().attributeExists("car"));
    }
    
    
 // ✅ 1-1. 페이징을 포함한 전체 자동차 리스트 조회 테스트
    @Test
    void testListWithPaging() throws Exception {
        mockMvc.perform(
                get("/car/list.do")
                .param("pageNum", "2")
                .param("pageSize", "5")
            )
            .andExpect(status().isOk())
            .andExpect(view().name("car/list"))
            .andExpect(model().attributeExists("carList"))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attributeExists("pageSize"))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attributeExists("totalCount"));
    }

}
