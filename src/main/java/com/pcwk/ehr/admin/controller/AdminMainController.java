package com.pcwk.ehr.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.member.MemberDTO;

@Controller
@RequestMapping("/admin")
public class AdminMainController {

	@GetMapping("/main.do")
	public String AdminMainController(HttpSession session, Model model) {
		
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			model.addAttribute("errorMessage", "로그인이 필요합니다.");
			return "redirect:/member/loginView.do";
		}
		
		if(loginUser.getAdminRole() != 1) {
			model.addAttribute("errorMessage","관리자 권한이 없습니다.");
			return "common/error";
		}
		
		model.addAttribute("loginUser", loginUser);
		return "admin/main";
		
	}
}
