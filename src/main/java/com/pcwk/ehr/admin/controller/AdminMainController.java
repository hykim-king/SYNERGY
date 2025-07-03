package com.pcwk.ehr.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.member.MemberDTO;
import com.pcwk.ehr.member.MemberService;

@Controller
@RequestMapping("/admin")
public class AdminMainController {
	
	@Autowired
	private MemberService memberService;

	@GetMapping("/main.do")
	public String AdminMainController(HttpSession session, Model model) {
		
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/member/loginView.do";
        }

        if (loginUser.getAdminRole() != 1) {
            model.addAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "common/error";
        }

        try {
            // DB에서 최신 회원 정보 가져오기
            MemberDTO updatedUser = memberService.doSelectOne(loginUser);
            session.setAttribute("loginUser", updatedUser); // 세션 갱신
            model.addAttribute("loginUser", updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "회원 정보 갱신 중 오류 발생");
            return "common/error";
        }

        return "admin/main";
		
	}
}
