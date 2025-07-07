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
@WebAppConfiguration  // 실제 웹 애플리케이션 컨텍스트를 로드하여 MockMvc를 설정
@ExtendWith(SpringExtension.class)  // JUnit 5에서 Spring TestContext Framework 사용
@ContextConfiguration(locations = { 
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",  // root ApplicationContext 설정
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"  // DispatcherServlet 설정
})
public class CarControllerTest {

    @Autowired
    private WebApplicationContext context;  // 스프링 웹 애플리케이션 컨텍스트

    private MockMvc mockMvc;  // HTTP 요청을 모킹하여 컨트롤러를 테스트하는 객체

    @BeforeEach  // 각 테스트 메서드 실행 전마다 호출
    void setUp() {
        // WebApplicationContext 기반으로 MockMvc 빌드
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    // 1. 전체 자동차 리스트 조회 테스트
    @Test
    void testList() throws Exception {
        // GET /car/list.do 를 호출하고,
        mockMvc.perform(get("/car/list.do"))
               .andExpect(status().isOk())  // HTTP 200(OK) 상태 확인
               .andExpect(view().name("car/list"));  // 반환된 뷰 이름 확인
    }

    // 1-1. 페이징을 포함한 전체 자동차 리스트 조회 테스트
    @Test
    void testListWithPaging() throws Exception {
        // pageNum=2, pageSize=5 파라미터로 GET 요청
        mockMvc.perform(get("/car/list.do")
                .param("pageNum", "2")  // 조회할 페이지 번호
                .param("pageSize", "5"))  // 한 페이지당 항목 수
               .andExpect(status().isOk())  // 성공 상태 검증
               .andExpect(view().name("car/list"));  // 뷰 이름 검증
    }

    // 2. 브랜드별 자동차 리스트 조회 테스트
    @Test
    void testListByBrand() throws Exception {
        // brand 파라미터 없이 최신 DTO명 carMf 기반 테스트 시 변경 필요
        mockMvc.perform(get("/car/brand.do").param("carMf", "현대"))  // 제조사 필터
               .andExpect(status().isOk())
               .andExpect(view().name("car/list"));
    }

    // 3. 자동차 상세 조회 테스트
    @Test
    void testDetail() throws Exception {
        // carCode=1 로 상세 조회
        mockMvc.perform(get("/car/detail.do").param("carCode", "1"))
               .andExpect(status().isOk())  // 성공 상태
               .andExpect(view().name("car/detail"));  // 상세 뷰 이름 검증
    }

    // 4. 자동차 정보 수정 테스트
    @Test
    void testUpdate() throws Exception {
        // 수정 폼 제출을 가정한 POST 요청
        mockMvc.perform(post("/car/update.do")
                .param("carCode", "1")           // 수정 대상 차량 코드
                .param("carMf", "현대")          // 필드명 carMf 바인딩
                .param("productName", "쏘나타")) // 필드명 productName 바인딩
               .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 검증
               .andExpect(redirectedUrlPattern("/car/detail.do?carCode=*"))  // 상세 페이지로 리다이렉트 패턴
               .andExpect(flash().attributeExists("msg"));  // 플래시 메시지 존재 여부 확인
    }

    // 5. 자동차 정보 삭제 테스트
    @Test
    void testDelete() throws Exception {
        // 삭제 요청 POST
        mockMvc.perform(post("/car/delete.do").param("carCode", "1"))
               .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 검증
               .andExpect(redirectedUrl("/car/list.do"))  // 목록 페이지로 정확히 리다이렉트
               .andExpect(flash().attributeExists("msg"));  // 플래시 메시지 존재 여부 확인
    }

}
