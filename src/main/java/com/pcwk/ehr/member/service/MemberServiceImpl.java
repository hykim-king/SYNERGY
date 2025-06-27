package com.pcwk.ehr.member.service;

import java.sql.SQLException;
import java.util.List;

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
	
	public String login(String id, String pwd) {
		MemberDTO param = new MemberDTO();
		param.setId(id);
		
		MemberDTO user = null;
		
		try {
			user = memberMapper.doSelectOne(param);
		}catch(Exception e){
			e.printStackTrace();
			return "데이터 베이스 오류가 발생했습니다.";
		}
		
		if(user == null) {
            return "등록된 ID는 존재 하지 않습니다.";
        }
		
		 if (!user.getId().equals(id)) {
		        return "ID가 일치하지 않습니다.";
		    }
		
		if(!user.getPwd().equals(pwd)) {
			return "비밀번호가 일치하지 않습니다.";
		}
		
		if(user.getAdminRole() == 1) {
			return user.getNickname() + "님 관리자 로그인 성공했습니다.";
		}
		
		return user.getNickname() + "님 로그인 성공했습니다.";
		
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
	public MemberDTO doSelectOne(MemberDTO dto) throws SQLException {
		return memberMapper.doSelectOne(dto);
	}

	@Override
	public int doUpdate(MemberDTO dto) {
		return memberMapper.doUpdate(dto);
	}

	@Override
	public int doDelete(MemberDTO dto) {
		return memberMapper.doDelete(dto);
	}

	@Override
	public List<MemberDTO> doRetrieve(MemberDTO param) {
		// TODO Auto-generated method stub
		return null;
	}



}
