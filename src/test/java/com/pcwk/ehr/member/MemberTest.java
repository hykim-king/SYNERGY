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
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class MemberTest {
	Logger log = LogManager.getLogger(getClass());
	
	
	@Autowired
	MemberMapper memberMapper;

	@Autowired
	ApplicationContext context;
	
	MemberDTO dto01;
	
	
	@BeforeEach
	void setUp() throws Exception {
		dto01 = new MemberDTO("test01","1234","hong","홍길동","010-1111-2222","hong12@naver.com",null,"admin",null,"admin",1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void isIdExists() {
		String existId = "test01";
		String nonExistId = "test02";
		
		int result1 = memberMapper.isIdExists(existId);
		int result2 = memberMapper.isIdExists(nonExistId);
		
		log.debug("ID '{}' 존재 여부 count: {}", existId, result1);
        log.debug("ID '{}' 존재 여부 count: {}", nonExistId, result2);

		
		assertEquals(1, result1);
		assertEquals(0, result2);
	}

	@Disabled
	@Test
	void doSave() {
		int result = memberMapper.doSave(dto01);
		assertEquals(1, result);
		
	}
	
	@Disabled
	@Test
	void bean() {
		assertNotNull(context);
		assertNotNull(memberMapper);

		log.debug(context);
		log.debug(memberMapper);
		
	}

}
