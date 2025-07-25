package com.pcwk.ehr.member;

import java.sql.SQLException;
import java.util.List;

public interface MemberService {

	int doSave(MemberDTO param) throws SQLException;

	MemberDTO doSelectOne(MemberDTO param) throws SQLException;

	int doUpdate(MemberDTO param);

	int doDelete(MemberDTO param);

	List<MemberDTO> doRetrieve(MemberDTO param) throws SQLException;
	
	String login(String id, String pwd) throws SQLException;

	int isIdExists(String id);
	
	int getCount() throws SQLException;
	
	int updatePasswordByUserId(String userId, String pwd);

}