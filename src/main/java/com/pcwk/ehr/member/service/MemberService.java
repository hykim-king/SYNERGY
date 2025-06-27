package com.pcwk.ehr.member.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.member.MemberDTO;

public interface MemberService {

	int doSave(MemberDTO param) throws SQLException;

	MemberDTO doSelectOne(MemberDTO param) throws SQLException;

	int doUpdate(MemberDTO param);

	int doDelete(MemberDTO param);

	List<MemberDTO> doRetrieve(MemberDTO param);
	
	String login(String id, String pwd) throws SQLException;

	int isIdExists(String id);

}