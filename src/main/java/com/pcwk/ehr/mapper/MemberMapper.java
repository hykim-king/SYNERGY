package com.pcwk.ehr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.member.MemberDTO;

@Mapper
public interface MemberMapper {
	
	int doSave(MemberDTO dto);
	
	int isIdExists(String id);	
}
