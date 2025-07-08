package com.pcwk.ehr.retailer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // 테스트 데이터 등록: 시퀀스로 PK 세팅
        RetailerDTO dto = new RetailerDTO(
            0,
            "TestProduct",
            "TestRetailer",
            "TestMf",
            "TestArea",
            "TestAddress",
            "010-0000-0000",
            new Date(System.currentTimeMillis()), // regDt
            "testAdmin",                          // regId
            new Date(System.currentTimeMillis()), // modDt
            "testAdmin"                           // modId
        );
        retailerMapper.doSave(dto);
        retailerCode = dto.getRetailerCode();
    }

    // 1. 페이징된 리테일러 목록 조회 테스트
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

    // 2. 전체 리테일러 목록 조회 테스트
    @Test
    void testAllList() throws Exception {
        mockMvc.perform(get("/retailer/all.do"))
            .andExpect(status().isOk())
            .andExpect(view().name("retailer/all"))
            .andExpect(model().attributeExists("retailerList"));
    }

    // 3. 리테일러 상세 조회 테스트
    @Test
    void testDetail() throws Exception {
        mockMvc.perform(get("/retailer/detail.do")
                .param("retailerCode", String.valueOf(retailerCode)))
            .andExpect(status().isOk())
            .andExpect(view().name("retailer/detail"))
            .andExpect(model().attributeExists("retailer"));
    }

    // 4. 리테일러 등록 처리 테스트
    @Test
    void testSave() throws Exception {
        mockMvc.perform(post("/retailer/save.do")
                .param("productName", "NewProduct")
                .param("retailerName", "NewRetailer")
                .param("carMf", "NewMf")
                .param("area", "NewArea")
                .param("address", "NewAddress")
                .param("telephone", "010-1111-2222")
                .param("regDt", "2025-07-07")
                .param("regId", "testAdmin")
                .param("modDt", "2025-07-07")
                .param("modId", "testAdmin"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/retailer/list.do"))
            .andExpect(flash().attributeExists("msg"));
    }

    // 5. 리테일러 수정 처리 테스트
    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(post("/retailer/update.do")
                .param("retailerCode", String.valueOf(retailerCode))
                .param("productName", "UpdProduct")
                .param("retailerName", "UpdRetailer")
                .param("carMf", "UpdMf")
                .param("area", "UpdArea")
                .param("address", "UpdAddress")
                .param("telephone", "010-2222-3333")
                .param("modDt", "2025-07-07")
                .param("modId", "testAdmin"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("/retailer/detail.do?retailerCode=*"))
            .andExpect(flash().attributeExists("msg"));
    }

    // 6. 리테일러 삭제 처리 테스트
    @Test
    void testDelete() throws Exception {
        mockMvc.perform(post("/retailer/delete.do")
                .param("retailerCode", String.valueOf(retailerCode)))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/retailer/list.do"))
            .andExpect(flash().attributeExists("msg"));
    }

}
