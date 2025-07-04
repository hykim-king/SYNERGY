package com.pcwk.ehr.retailer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;

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

import com.pcwk.ehr.mapper.Retailermapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class RetailerControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Retailermapper retailerMapper;

    private MockMvc mockMvc;

    private int retailerCode;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // 테스트 데이터 삽입
        RetailerDTO testRetailer = new RetailerDTO(
            0, // 시퀀스로 대체됨
            "테스트 차량",
            "테스트 대리점",
            "테스트제조사",
            "서울",
            "서울시 강남구 테스트로 1",
            "02-1111-2222",
            new Date(System.currentTimeMillis()),
            "testAdmin",
            new Date(System.currentTimeMillis()),
            "testAdmin"
        );

        retailerMapper.doSave(testRetailer);
        this.retailerCode = testRetailer.getRetailerCode(); // 시퀀스로 설정된 실제 PK 가져오기
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
        mockMvc.perform(get("/retailer/detail")
                .param("retailerCode", String.valueOf(retailerCode)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("retailer"))
            .andExpect(view().name("retailer/detail"));
    }

}
