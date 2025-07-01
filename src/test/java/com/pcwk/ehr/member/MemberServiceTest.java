package com.pcwk.ehr.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.List;

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
class MemberServiceTest {
Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	MemberMapper memberMapper;

	@Autowired
	ApplicationContext context;
	
	@Autowired
	MemberService memberService;
	
	MemberDTO dto01;
	MemberDTO adminLogin;
	MemberDTO userLogin;
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		dto01 = new MemberDTO("test01","1234","hong","홍길동","010-1111-2222","hong12@naver.com",null,"admin",null,"admin",1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	//@Disabled
	@Test
	void login() throws SQLException{
		
		memberMapper.deleteAll();
		
		adminLogin = new MemberDTO(
		        "admin01", "adminpw", "관리자", "관리자이름", "010-0000-1111", "admin@site.com",
		        null, "admin", null, "admin", 1);
		memberService.doSave(adminLogin);
		
		userLogin = new MemberDTO("user01", "userpw", "홍길동", "홍길동",
                "010-2222-3333", "user@site.com", null, "admin", null, "admin", 0);
		memberService.doSave(userLogin);
		
		String adminResult = memberService.login("admin01", "adminpw");
		log.debug("관리자 로그인 결과: {}", adminResult);
		assertEquals("관리자님 관리자 로그인 성공했습니다.", adminResult);

		String userResult = memberService.login("user01", "userpw");
		log.debug("일반 사용자 로그인 결과: {}", userResult);
		assertEquals("홍길동님 로그인 성공했습니다.", userResult);

		String wrongPwResult = memberService.login("user01", "wrongpw");
		log.debug("비밀번호 오류 로그인 결과: {}", wrongPwResult);
		assertEquals("비밀번호가 일치하지 않습니다.", wrongPwResult);

		String notExistIdResult = memberService.login("notExist", "1234");
		log.debug("존재하지 않는 ID 로그인 결과: {}", notExistIdResult);
		assertEquals("등록된 ID는 존재 하지 않습니다.", notExistIdResult);

	}
	
	//@Disabled
	@Test
	void doSelectOneTest() throws SQLException {
		memberMapper.deleteAll(); 
	    memberService.doSave(dto01);

	    MemberDTO searchDto = new MemberDTO();
	    searchDto.setId(dto01.getId());

	    MemberDTO outDto = memberService.doSelectOne(searchDto);
	    log.debug("doSelectOne 결과: {}", outDto);

	    assertNotNull(outDto);
	    assertEquals(dto01.getId(), outDto.getId());
	}
	
	//@Disabled
	@Test
	void doUpdateTest() throws SQLException {

		memberMapper.deleteAll();
	    memberService.doSave(dto01);


	    dto01.setName("홍길순");
	    dto01.setPhone("010-9999-8888");
	    dto01.setEmail("hongsoo@example.com");
	    dto01.setModId("admin");

	    int updateCount = memberService.doUpdate(dto01);
	    log.debug("doUpdate 결과: 수정된 행 수 = {}", updateCount);
	    assertEquals(1, updateCount);


	    MemberDTO updatedDto = memberService.doSelectOne(dto01);
	    assertEquals("홍길순", updatedDto.getName());
	    assertEquals("010-9999-8888", updatedDto.getPhone());
	    assertEquals("hongsoo@example.com", updatedDto.getEmail());
	}
	
	//@Disabled
	@Test
	void doDeleteTest() throws SQLException {

		memberMapper.deleteAll();
	    memberService.doSave(dto01);

	    int deleteCount = memberService.doDelete(dto01);
	    log.debug("doDelete 결과: 삭제된 행 수 = {}", deleteCount);
	    assertEquals(1, deleteCount);

	    // 삭제 후 존재여부 확인
	    int existCount = memberService.isIdExists(dto01.getId());
	    assertEquals(0, existCount);
	}

	//@Disabled
	@Test
	void doRetrieveTest() throws SQLException {
		memberMapper.deleteAll();
	    memberService.doSave(dto01);

	    dto01.setSearchDiv("10");      
	    dto01.setSearchWord("홍길");
	    dto01.setPageNum(1);
	    dto01.setPageSize(10);

	    List<MemberDTO> list = memberService.doRetrieve(dto01);

	    log.debug("doRetrieve 결과 개수: {}", list.size());
	    for (MemberDTO m : list) {
	        log.debug(m);

	        assertNotNull(m.getName());
	        assert(m.getName().contains("홍길"));
	    }
	}
	
	//@Disabled
	@Test
	void doSave() throws SQLException {
		memberMapper.deleteAll();
		
		int saveResult = memberService.doSave(dto01);
		log.debug("1. 저장 결과 : {}", saveResult);
	    assertEquals(1, saveResult, "회원 저장 실패");
		
	}
	//@Disabled
	@Test
	void IsIdExistsTest() throws SQLException {
		memberMapper.deleteAll();
		memberMapper.doSave(dto01);
		
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
