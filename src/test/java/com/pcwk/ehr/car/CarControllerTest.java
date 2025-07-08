package com.pcwk.ehr.car;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// Spring MVC 테스트 환경 구성 애노테이션:
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
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    // 1. 전체 자동차 리스트 조회 테스트 (파라미터 없이)
    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/car/list.do"))
               .andExpect(status().isOk())
               .andExpect(view().name("car/list"));
    }

    // 1-1. 페이징을 포함한 전체 자동차 리스트 조회 테스트
    @Test
    void testListWithPaging() throws Exception {
        mockMvc.perform(get("/car/list.do")
                .param("pageNum", "2")
                .param("pageSize", "5"))
               .andExpect(status().isOk())
               .andExpect(view().name("car/list"));
    }

    // 2. 브랜드별 자동차 리스트 조회 테스트
    @Test
    void testListByBrand() throws Exception {
        mockMvc.perform(get("/car/brand.do").param("brand", "현대"))
               .andExpect(status().isOk())
               .andExpect(view().name("car/list"));
    }

    // 3. 자동차 상세 조회 테스트
    @Test
    void testDetail() throws Exception {
        mockMvc.perform(get("/car/detail.do").param("carCode", "1"))
               .andExpect(status().isOk())
               .andExpect(view().name("car/detail"));
    }

    // 4. 자동차 정보 수정 테스트
    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(post("/car/update.do")
                .param("carCode", "1")
                .param("carMf", "현대")
                .param("productName", "쏘나타"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("/car/detail.do?carCode=*"))
               .andExpect(flash().attributeExists("msg"));
    }

    // 5. 자동차 정보 삭제 테스트
    @Test
    void testDelete() throws Exception {
        mockMvc.perform(post("/car/delete.do").param("carCode", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/car/list.do"))
               .andExpect(flash().attributeExists("msg"));
    }

}
