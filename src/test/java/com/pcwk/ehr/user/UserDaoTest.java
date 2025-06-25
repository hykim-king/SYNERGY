/**
 * Package Name : com.pcwk.ehr.user <br/>
 * 파일명: UsermapperTest.java <br/>
 */
package com.pcwk.ehr.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.UserDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class UserDaoTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	UserMapper mapper;

	@Autowired
	ApplicationContext context;
	
	UserDTO dto01;
	UserDTO dto02;
	UserDTO dto03;
	
	SearchDTO search;


	@BeforeEach
	public void setUp() throws Exception {

		log.debug("context:" + context);

		dto01 = new UserDTO("pcwk01", "이상무01", "4321a", "사용안함", 0, 0, Level.BASIC, "dlwhd0614@naver.com");
		dto02 = new UserDTO("pcwk02", "이상무02", "4321b", "사용안함", 55, 10, Level.SILVER, "dlwhd0614@naver.com");
		dto03 = new UserDTO("pcwk03", "이상무03", "4321c", "사용안함", 100, 40, Level.GOLD, "dlwhd0614@naver.com");
		
		search = new SearchDTO();

	}

	@AfterEach
	public void tearDown() throws Exception {
		log.debug("***************************");
		log.debug("@After");
		log.debug("***************************");
	}

	//@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);

		log.debug(context);
		log.debug(mapper);
	}

	@Disabled
	@Test
	public void getFailure() throws SQLException {
		mapper.deleteAll();

		mapper.doSave(dto01);
		int count = mapper.getCount();
		assertEquals(1, count);

		String unknownId = dto01.getUserId() + "_99";
		dto01.setUserId(unknownId);

		assertThrows(EmptyResultDataAccessException.class, () -> {
			UserDTO outVO = mapper.doSelectOne(dto01);
		});
	}

	//@Disabled
	@Test
	void doRetrieve() throws SQLException {
		mapper.deleteAll();

		int count = mapper.saveAll();
		log.debug("count:" + count);

		assertEquals(502, count);
		
		search.setPageSize(10);
		search.setPageNo(1);
		
		search.setSearchDiv("10");
		search.setSearchWord("jamesol");
		

		List<UserDTO> list = mapper.doRetrieve(search);
		for (UserDTO vo : list) {
			log.debug(vo);
		}
	}

	//@Disabled
	@Test
	void dodelete() throws SQLException {
		mapper.deleteAll();

		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		int count = mapper.getCount();
		assertEquals(count, 1);

		mapper.doDelete(dto01);
		assertEquals(1, flag);

		count = mapper.getCount();
		assertEquals(0, count);
	}

	//@Disabled
	@Test
	void doUpdate() throws SQLException {
		mapper.deleteAll();

		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		int count = mapper.getCount();
		assertEquals(count, 1);

		UserDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		isSameUser(outVO, dto01);

		String upString = "_U";
		int upInt = 999;
		outVO.setName(outVO.getName() + upString);
		outVO.setPassword(outVO.getPassword() + upString);
		outVO.setLogin(outVO.getLogin() + upInt);
		outVO.setRecommand(outVO.getRecommand() + upInt);
		outVO.setGrade(outVO.getGrade().GOLD);
		outVO.setEmail(outVO.getEmail() + upString);

		log.debug("outVO:" + outVO);

		flag = mapper.doUpdate(outVO);
		assertEquals(1, flag);

		UserDTO upVO = mapper.doSelectOne(outVO);

		isSameUser(outVO, upVO);

	}

	//@Disabled
	@Test
	public void getAll() throws SQLException {
		mapper.deleteAll();

		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		int count = mapper.getCount();
		assertEquals(count, 1);

		mapper.doSave(dto02);
		count = mapper.getCount();
		assertEquals(count, 2);

		mapper.doSave(dto03);
		count = mapper.getCount();
		assertEquals(count, 3);

		List<UserDTO> userList = mapper.getAll();

		assertEquals(userList.size(), 3);

		for (UserDTO dto : userList) {
			log.debug(dto);
		}

	}

	//@Disabled
	@Test
	public void addAndGet() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1.전체 삭제
		// 2.단건 등록
		// 2.1 전체 건수 조회
		// 3.단건 조회
		// 4.비교

		mapper.deleteAll();

		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		int count = mapper.getCount();
		assertEquals(count, 1);

		mapper.doSave(dto02);
		count = mapper.getCount();
		assertEquals(count, 2);

		mapper.doSave(dto03);
		count = mapper.getCount();
		assertEquals(count, 3);

		UserDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		isSameUser(outVO, dto01);

		UserDTO outVO2 = mapper.doSelectOne(dto02);
		assertNotNull(outVO2);
		isSameUser(outVO2, dto02);

		UserDTO outVO3 = mapper.doSelectOne(dto03);
		assertNotNull(outVO3);
		isSameUser(outVO3, dto03);

	}

	// 데이터 비교
	public void isSameUser(UserDTO outVO, UserDTO dto01) {
		assertEquals(outVO.getUserId(), dto01.getUserId());
		assertEquals(outVO.getName(), dto01.getName());
		assertEquals(outVO.getPassword(), dto01.getPassword());
		assertEquals(outVO.getLogin(), dto01.getLogin());
		assertEquals(outVO.getRecommand(), dto01.getRecommand());
		assertEquals(outVO.getGrade(), dto01.getGrade());
		assertEquals(outVO.getEmail(), dto01.getEmail());
	}

}
