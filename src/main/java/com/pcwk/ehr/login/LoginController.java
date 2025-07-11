package com.pcwk.ehr.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String loginView(HttpSession session) {
		return "member/login";
	}

	@PostMapping("/login.do")
	public String login(String id, String pwd, HttpSession session, RedirectAttributes redirectAttrs) {

	    MemberDTO param = new MemberDTO();
	    param.setId(id);

	    MemberDTO user = null;

	    try {
	        user = memberService.doSelectOne(param);
	    } catch (Exception e) {
	        redirectAttrs.addFlashAttribute("message", "서버 오류가 발생했습니다.");
	        return "redirect:/member/loginView.do";
	    }

	    if (user == null) {
	        redirectAttrs.addFlashAttribute("message", "존재하지 않는 ID입니다.");
	        return "redirect:/member/loginView.do";
	    }

	    if (!user.getPwd().equals(pwd)) {
	        redirectAttrs.addFlashAttribute("message", "비밀번호가 일치하지 않습니다.");
	        return "redirect:/member/loginView.do";
	    }

	    // 로그인 성공
	    session.setAttribute("loginUser", user);

	    String nick = (user.getNickname() != null && !user.getNickname().isEmpty()) ? user.getNickname() : user.getId();
	    session.setAttribute("welcomeMessage", nick + " 님 환영합니다.");

	    if (user.getAdminRole() == 1) {
	        redirectAttrs.addFlashAttribute("message", "관리자 로그인 성공!");
	        return "redirect:/admin/main.do";
	    } else {
	        redirectAttrs.addFlashAttribute("message", "로그인 성공!");
	        return "redirect:/main/main.do";
	    }
	}

	// 로그아웃
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		// 로그아웃하는 명령어
		session.invalidate();
		return "redirect:/main/main.do";
	}

}
