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
    
    @PostMapping("updateInfo.do")
    public String updateInfo(MemberDTO member, HttpSession session, Model model) {
    	
    	 MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
    	    if (loginUser == null) {
    	        return "redirect:/member/loginView.do";
    	    }

    	    try {
    	        // 아이디는 로그인한 유저 아이디로 고정 (보안상)
    	        member.setId(loginUser.getId());

    	        // 비밀번호가 null 또는 빈 값이면 기존 비밀번호 유지
    	        if (member.getPwd() == null || member.getPwd().isEmpty()) {
    	            member.setPwd(loginUser.getPwd());
    	        }

    	        int result = memberService.doUpdate(member);

    	        if (result == 1) {
    	            // 업데이트 성공 시 session의 loginUser 정보도 최신화
    	            session.setAttribute("loginUser", member);
    	            return "redirect:/member/mypage.do";
    	        } else {
    	            model.addAttribute("errorMessage", "정보 수정에 실패했습니다.");
    	            model.addAttribute("Info", member);
    	            return "member/mypage";
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        model.addAttribute("errorMessage", "오류가 발생했습니다.");
    	        model.addAttribute("Info", member);
    	        return "member/mypage";
    	    }
    	
    }
    
    // ✅ [1] 비밀번호 찾기 폼 (아이디 입력)
    @GetMapping("/passwordFind.do")
    public String passwordFindForm() {
        return "member/passwordFind"; // JSP: /WEB-INF/views/member/passwordFind.jsp
    }

    // ✅ [2] 아이디 확인 → 재설정 폼으로
    @PostMapping("/passwordFind.do")
    public String passwordFind(@RequestParam("userId") String userId, Model model) {
        int count = memberService.isIdExists(userId);
        if (count > 0) {
            model.addAttribute("userId", userId); // reset 폼에 전달
            return "member/passwordReset"; // JSP: /WEB-INF/views/member/passwordReset.jsp
        } else {
            model.addAttribute("error", "존재하지 않는 아이디입니다.");
            return "member/passwordFind";
        }
    }

    // ✅ [3] 비밀번호 재설정
    @PostMapping("/passwordReset.do")
    public String passwordReset(
            @RequestParam("userId") String userId,
            @RequestParam("pwd") String pwd,
            Model model) {

        int result = memberService.updatePasswordByUserId(userId, pwd);
        if (result > 0) {
            model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            return "member/login";
        } else {
            model.addAttribute("error", "비밀번호 변경에 실패했습니다. 아이디를 확인해주세요.");
            model.addAttribute("userId", userId); // 실패 시 값 유지
            return "member/passwordReset";
        }
    }
    
    
}


