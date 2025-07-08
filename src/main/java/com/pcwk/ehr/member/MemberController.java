package com.pcwk.ehr.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	public MemberController() {
		
	}
	
	@GetMapping("/registerView.do")
	public String registerView() {
		return "member/register";
	}
	
	@PostMapping("register.do")
	public String register(MemberDTO member, Model model, HttpSession session) {
	    try {
	        int result = memberService.doSave(member);
	        if (result == 1) {
	            session.setAttribute("message", "회원가입이 완료되었습니다.");
	            return "redirect:/member/loginView.do";
	        } else {
	            model.addAttribute("errorMessage", "회원가입에 실패했습니다.");
	            return "member/register";
	        }
	    } catch (Exception e) {
	        // 에러 메시지를 상세하게 보여주지 않고 간단히 처리
	    	 model.addAttribute("errorMessage", "회원가입에 실패했습니다.");
	        // 필요하면 로그로 상세 에러 기록
	        e.printStackTrace();
	        return "member/register";
	    }
	}
	
    @GetMapping("/checkId.do")
    @ResponseBody
    public String checkId(@RequestParam("id") String id) {
        try {
            int count = memberService.isIdExists(id);
            return count > 0 ? "duplicate" : "available";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @GetMapping("/mypage.do")
    public String mypage(HttpSession session, Model model) {
    	
    	MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
    	
    	if(loginUser == null) {
    		return "redirect:/member/loginView.do";
    	}
    	
    	try {
    		MemberDTO Info = memberService.doSelectOne(loginUser);
    		model.addAttribute("Info", Info);
    		return "member/mypage";
    	}catch(Exception e){
    		e.printStackTrace();
    		model.addAttribute("errorMessage", "회원 정보를 불러오지 못했습니다.");
    	}
    	
    	return "member/mypage";
    }
    
    
}


