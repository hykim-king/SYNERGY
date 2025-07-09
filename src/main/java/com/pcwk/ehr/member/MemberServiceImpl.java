package com.pcwk.ehr.member;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.mapper.MemberMapper;
import com.pcwk.ehr.member.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {

	Logger log = LogManager.getLogger(getClass());

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
		} catch (Exception e) {
			e.printStackTrace();
			return "데이터 베이스 오류가 발생했습니다.";
		}

		log.debug("login(): user = {}", user);
		log.debug("login(): adminRole = {}", (user != null ? user.getAdminRole() : "null"));
		log.debug("login(): nickname = {}", (user != null ? user.getNickname() : "null"));

		if (user == null) {
			return "등록된 ID는 존재 하지 않습니다.";
		}

		if (!user.getId().equals(id)) {
			return "ID가 일치하지 않습니다.";
		}

		if (!user.getPwd().equals(pwd)) {
			return "비밀번호가 일치하지 않습니다.";
		}

		if (user.getAdminRole() == 1) {
			return user.getNickname() + "님 관리자 로그인 성공했습니다.";
		}

		return user.getNickname() + "님 로그인 성공했습니다.";

	}

	@Override
	public int doSave(MemberDTO dto) {

		Date now = new Date();
	    dto.setRegDt(now);
	    dto.setModDt(now);

	    // 등록자와 수정자 ID 세팅 (null 또는 빈 문자열이면 이름 또는 ID 사용)
	    if (dto.getRegId() == null || dto.getRegId().isEmpty()) {
	        dto.setRegId(dto.getName() != null && !dto.getName().isEmpty() ? dto.getName() : dto.getId());
	    }
	    if (dto.getModId() == null || dto.getModId().isEmpty()) {
	        dto.setModId(dto.getName() != null && !dto.getName().isEmpty() ? dto.getName() : dto.getId());
	    }

	    int result = memberMapper.doSave(dto);

	    if (result > 0) {
	        // 저장 성공 시 DB에서 다시 조회하여 regDt, modDt 필드 최신값 채우기
	        MemberDTO saved = memberMapper.doSelectOne(dto);
	        dto.setRegDt(saved.getRegDt());
	        dto.setModDt(saved.getModDt());
	    }

		    return result;
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
		if (dto.getModId() == null || dto.getModId().isEmpty()) {
	        dto.setModId(dto.getName()); // 또는 dto.getId()
	    }

	    int result = memberMapper.doUpdate(dto);  // update 수행

	    if(result > 0) {
	        MemberDTO updated = memberMapper.doSelectOne(dto); // 다시 조회해서 최신 modDt 가져오기
	        dto.setModDt(updated.getModDt());
	    }

	    return result;
		}

	@Override
	public int doDelete(MemberDTO dto) {
		return memberMapper.doDelete(dto);
	}

	@Override
	public List<MemberDTO> doRetrieve(MemberDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return memberMapper.doRetrieve(dto);
	}

	@Override
	public int getCount() throws SQLException {
		
		return memberMapper.getCount();
	}

	@Override
	public int updatePasswordByUserId(String userId, String pwd) {
		Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("pwd", pwd);
        return memberMapper.updatePasswordByUserId(param);
	}

}
