package com.pcwk.ehr.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.mapper.MemberMapper;
import com.pcwk.ehr.member.service.MemberService;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class memberServiceTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	MemberMapper memberMapper;

	@Autowired
	ApplicationContext context;
	
	@Autowired
	MemberService memberService;
	
	MemberDTO dto01;
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		dto01 = new MemberDTO("test01","1234","hong","홍길동","010-1111-2222","hong12@naver.com",null,"admin",null,"admin",1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Disabled
	@Test
	void doSave() {
		int saveResult = memberService.doSave(dto01);
		log.debug("1. 저장 결과 : {}", saveResult);
	    assertEquals(1, saveResult, "회원 저장 실패");
		
	}
	@Disabled
	@Test
	void IsIdExistsTest() {
		int existsResult = memberService.isIdExists(dto01.getId());
		log.debug("2. 중복 확인 결과 : {}", existsResult);
        assertEquals(1, existsResult, "ID 중복 확인 실패");
	}

	//@Disabled
	@Test
	void bean() {
		assertNotNull(context);
		assertNotNull(memberMapper);
		assertNotNull(memberService);

		log.debug(context);
		log.debug(memberMapper);
		log.debug(memberService);
	}

}
