package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.member.MemberDTO;

@Mapper
public interface MemberMapper extends WorkDiv<MemberDTO> {
	
	int isIdExists(String id);	
	
	int saveAll();
	
	List<MemberDTO> getAll();
	
	void deleteAll() throws SQLException;
	
	int getCount() throws SQLException;
}
