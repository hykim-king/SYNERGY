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
	
	
	//@Disabled
	@Test
	void doRetrieve() throws Exception{
		
		memberMapper.deleteAll();
		
		for (int i = 1; i <= 3; i++) {
	        MemberDTO member = new MemberDTO(
	            "test0" + i,
	            "1234",
	            "nick" + i,
	            "홍길동" + i,
	            "010-0000-000" + i,
	            "test0" + i + "@test.com",
	            null,
	            "admin",
	            null,
	            "admin",
	            1
	        );
	        int result = memberMapper.doSave(member);
	        assertEquals(1, result, "등록 실패!");
	    }

	    // 3. 검색 조건 설정 (이름으로 검색)
	    MemberDTO search = new MemberDTO();
	    search.setSearchDiv("10"); // 이름
	    search.setSearchWord("홍길동");
	    search.setPageSize(10);
	    search.setPageNum(1);

	    log.debug("┌───────────────────────────────────────┐");
	    log.debug("│ doRetrieve 테스트 시작                                │");
	    log.debug("└───────────────────────────────────────┘");

	    // 4. 목록 조회
	    List<MemberDTO> list = memberMapper.doRetrieve(search);

	    // 5. 검증
	    assertEquals(3, list.size(), "조회된 목록 수가 예상과 다릅니다.");

	    for (MemberDTO dto : list) {
	        log.debug("조회 결과: {}", dto);
	    }

	    log.debug("┌───────────────────────────────────────┐");
	    log.debug("│ doRetrieve 테스트 종료                                │");
	    log.debug("└───────────────────────────────────────┘");
		
		
	}
	
	//@Disabled
	@Test
	void doUpdate() throws Exception{
		
		memberMapper.deleteAll();
		memberMapper.doSave(dto01);
		
		MemberDTO updateDto = new MemberDTO();
	    updateDto.setId(dto01.getId()); // 수정 대상 ID
	    updateDto.setPwd("5678");
	    updateDto.setNickname("park");
	    updateDto.setName("박길동");
	    updateDto.setPhone("010-9999-8888");
	    updateDto.setEmail("park99@naver.com");
	    updateDto.setModId("admin"); // 수정자
	    updateDto.setAdminRole(0);

	    log.debug("┌───────────────────────────────────────────┐");
	    log.debug("│ doUpdate 테스트 시작                       │");
	    log.debug("│ 수정할 회원 정보: {}", updateDto);
	    log.debug("└───────────────────────────────────────────┘");

	    // 3. 수정 실행
	    int updateCount = memberMapper.doUpdate(updateDto);
	    assertEquals(1, updateCount);

	    // 4. 결과 조회 및 검증
	    MemberDTO outDto = memberMapper.doSelectOne(updateDto);
	    assertNotNull(outDto);
	    assertEquals("5678", outDto.getPwd());
	    assertEquals("박길동", outDto.getName());
	    assertEquals("park", outDto.getNickname());
	    assertEquals("010-9999-8888", outDto.getPhone());
	    assertEquals("park99@naver.com", outDto.getEmail());
	    assertEquals(0, outDto.getAdminRole());

	    log.debug("수정 후 조회 결과: {}", outDto);

	    log.debug("┌───────────────────────────────────────────┐");
	    log.debug("│ doUpdate 테스트 종료                       │");
	    log.debug("└───────────────────────────────────────────┘");
		
	}
	
	//@Disabled
	@Test
	void doSelectOne() throws Exception{
		
		memberMapper.deleteAll();
		memberMapper.doSave(dto01);
		
		log.debug("┌───────────────────────────────────────────┐");
	    log.debug("│ doSelectOne 테스트 시작                      │");
	    log.debug("│ 조회할 회원 ID: {}", dto01.getId());
	    log.debug("└───────────────────────────────────────────┘");

	    MemberDTO outDto = memberMapper.doSelectOne(dto01);

	    assertNotNull(outDto);
	    assertEquals(dto01.getId(), outDto.getId());

	    log.debug("조회 결과: {}", outDto);

	    log.debug("┌───────────────────────────────────────────┐");
	    log.debug("│ doSelectOne 테스트 종료                      │");
	    log.debug("└───────────────────────────────────────────┘");
	}

	//@Disabled
	@Test
	void doDelete() throws SQLException {
		
		memberMapper.deleteAll();
		memberMapper.doSave(dto01);
		
		log.debug("┌───────────────────────────────────────────┐");
	    log.debug("│ doDelete 테스트 시작                                                  │");
	    log.debug("│ 삭제할 회원 ID: {}", dto01.getId( ));   
	    log.debug("└───────────────────────────────────────────┘");
		
		int count = memberMapper.doDelete(dto01);
		log.debug("삭제된 회원 수: {}", count);
		
		assertEquals(1, count);
		
		int existCount = memberMapper.isIdExists(dto01.getId());
		log.debug("삭제 후 해당 ID 존재 여부: {}", existCount);
		assertEquals(0, existCount);
	}
	

	//@Disabled
	@Test
	void doSave() throws SQLException {
		
		memberMapper.deleteAll();
		int result = memberMapper.doSave(dto01);
		assertEquals(1, result);
		
	}
	//@Disabled
	@Test
	void isIdExists() throws SQLException {
		
		memberMapper.deleteAll();
		memberMapper.doSave(dto01);
		
		String existId = "test01";
		String nonExistId = "test02";
		
		int result1 = memberMapper.isIdExists(existId);
		int result2 = memberMapper.isIdExists(nonExistId);
		
		log.debug("ID '{}' 존재 여부 count: {}", existId, result1);
		log.debug("ID '{}' 존재 여부 count: {}", nonExistId, result2);
		
		//1 이면 id가 등록되어 있음, 0은 id가 등록되어 있지 않음
		assertEquals(1, result1);
		assertEquals(0, result2);
	}
	
	//@Disabled
	@Test
	void bean() {
		assertNotNull(context);
		assertNotNull(memberMapper);

		log.debug(context);
		log.debug(memberMapper);
		
	}

}
