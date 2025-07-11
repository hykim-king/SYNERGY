package com.pcwk.ehr.admin.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcwk.ehr.member.MemberDTO;
import com.pcwk.ehr.member.MemberService;

@Controller
@RequestMapping("/admin/member")
public class MemberAdminController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/list.do")
	public String list(MemberDTO param, Model model) {
	    try {
	        // 기본값 세팅
	        if (param.getPageNum() < 1) param.setPageNum(1);
	        if (param.getPageSize() < 1) param.setPageSize(10);

	        // startRow, endRow 계산
	        int startRow = (param.getPageNum() - 1) * param.getPageSize() + 1;
	        int endRow = param.getPageNum() * param.getPageSize();

	        param.setStartRow(startRow);
	        param.setEndRow(endRow);

	        // 회원 목록 조회
	        List<MemberDTO> members = memberService.doRetrieve(param);

	        // 전체 건수 (검색 조건 없이)
	        int totalCount = memberService.getCount(); // 인자 없이 전체 수 조회
	        int totalPages = (int) Math.ceil((double) totalCount / param.getPageSize());

	        // 모델 전달
	        model.addAttribute("members", members);
	        model.addAttribute("currentPage", param.getPageNum());
	        model.addAttribute("pageSize", param.getPageSize());
	        model.addAttribute("totalPages", totalPages);
	        model.addAttribute("totalCount", totalCount);

	    } catch (SQLException e) {
	        model.addAttribute("errorMessage", "회원 목록 조회 중 오류가 발생했습니다.");
	    }

	    return "admin/member/list";
	}
	
	@GetMapping("/registerView.do")
	public String registerView() {
		return "admin/member/register";
	}
	
	@PostMapping("register.do")
	public String register(MemberDTO member, Model model, HttpSession session) {
	    try {
	        int result = memberService.doSave(member);
	        if (result == 1) {
	            session.setAttribute("message", "회원등록이 완료되었습니다.");
	            return "redirect:/admin/main.do";
	        } else {
	            model.addAttribute("errorMessage", "회원등록에 실패했습니다.");
	            return "/admin/member/register";
	        }
	    } catch (Exception e) {
	        // 에러 메시지를 상세하게 보여주지 않고 간단히 처리
	    	 model.addAttribute("errorMessage", "회원등록에 실패했습니다.");
	        e.printStackTrace();
	        return "/admin/member/register";
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
    
    @GetMapping("/updateView.do")
    public String updateView(@RequestParam("id") String id, Model model) {
    	try {
    		MemberDTO param = new MemberDTO();
    		param.setId(id);
    		MemberDTO member = memberService.doSelectOne(param);
    		
    		if(member == null) {
    			model.addAttribute("errorMessage", "해당 회원 정보를 찾을 수 없습니다.");
    			return "redirect:/admin/member/list.do";
    		}
    		model.addAttribute("member", member);
    		return "admin/member/update";
    	}catch (Exception e) {
    		model.addAttribute("errorMessage", "회원정보 조회 중 오류가 발생했습니다.");
    		e.printStackTrace();
    		return "/admin/member/list";
    	}
    }
    
    @PostMapping("/update.do")
	public String update(MemberDTO member, Model model, HttpSession session) {
		try {
			// 세션에서 로그인 ID를 수정자로 지정
			String loginId = (String) session.getAttribute("loginId");
			member.setModId(loginId);

			int result = memberService.doUpdate(member);

			if (result == 1) {
				session.setAttribute("message", "회원정보가 성공적으로 수정되었습니다.");
				return "redirect:/admin/member/list.do";
			} else {
				model.addAttribute("errorMessage", "회원정보 수정에 실패했습니다.");
				model.addAttribute("member", member);
				return "admin/member/update";
			}
		} catch (Exception e) {
			model.addAttribute("errorMessage", "회원정보 수정 중 오류가 발생했습니다.");
			model.addAttribute("member", member);
			e.printStackTrace();
			return "admin/member/update";
		}
	}
    
    @GetMapping("/delete.do")
    public String delete(@RequestParam("id") String id, Model model, HttpSession session) {
        try {
            MemberDTO param = new MemberDTO();
            param.setId(id);
            
            int result = memberService.doDelete(param);
            if (result == 1) {
                session.setAttribute("message", "회원이 삭제되었습니다.");
            } else {
                model.addAttribute("errorMessage", "회원 삭제에 실패했습니다.");
                return "admin/member/list";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원 삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
            return "admin/member/list";
        }
        return "redirect:/admin/member/list.do";//POST 처리 후 다른 페이지로 이동할 때 redirect를 씀
    }
}
