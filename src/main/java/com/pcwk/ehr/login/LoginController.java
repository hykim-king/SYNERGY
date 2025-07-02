package com.pcwk.ehr.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.member.MemberDTO;
import com.pcwk.ehr.member.MemberService;

@Controller
@RequestMapping("/member")
public class LoginController implements PLog {

	@Autowired
	private MemberService memberService;

	public LoginController() {

	}

	@GetMapping("/loginView.do")
	public String loginView() {
		return "member/login";
	}

	@PostMapping("/login.do")
	public String login(String id, String pwd, HttpSession session, Model model) {

		MemberDTO param = new MemberDTO();
		param.setId(id);
		
		MemberDTO user = null;

		try {
	        user = memberService.doSelectOne(param); // DB에서 사용자 정보 조회
	    } catch (Exception e) {
	        model.addAttribute("message", "서버 오류가 발생했습니다.");
	        return "member/login";
	    }

	    if (user == null) {
	        model.addAttribute("message", "존재하지 않는 ID입니다.");
	        return "member/login";
	    }

	    if (!user.getPwd().equals(pwd)) {
	        model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
	        return "member/login";
	    }
	    //사용자가 로그인에 성공했을 때, 해당 사용자 정보를 세션에 "loginUser"라는 이름으로 저장
	    session.setAttribute("loginUser", user);
	    
		if (user.getAdminRole() == 1) {
			//redirect:/admin/main.do -> /admin/main.do로 이동
	        return "redirect:/admin/main.do";
	    } else {
	        return "redirect:/member/main.do";
	    }
}

	// 로그아웃
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		// 로그아웃하는 명령어
		session.invalidate();
		return "redirect:/member/loginView.do";
	}

}
