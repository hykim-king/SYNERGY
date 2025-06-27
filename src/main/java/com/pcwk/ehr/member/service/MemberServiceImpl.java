package com.pcwk.ehr.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.mapper.MemberMapper;
import com.pcwk.ehr.member.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	public MemberServiceImpl() {
		
	}
	
	@Override
	public int doSave(MemberDTO dto) {
		return memberMapper.doSave(dto);
	}

	@Override
	public int isIdExists(String id) {
		return memberMapper.isIdExists(id);
	}

	@Override
	public MemberDTO doSelectOne(MemberDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}


}
