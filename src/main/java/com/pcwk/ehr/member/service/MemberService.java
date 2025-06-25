package com.pcwk.ehr.member.service;

import com.pcwk.ehr.member.MemberDTO;

public interface MemberService {

	int doSave(MemberDTO dto);

	int isIdExists(String id);

}