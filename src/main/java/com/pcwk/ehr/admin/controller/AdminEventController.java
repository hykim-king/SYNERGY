package com.pcwk.ehr.admin.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.event.EventDTO;
import com.pcwk.ehr.event.EventService;
import com.pcwk.ehr.member.MemberDTO;

@Controller
@RequestMapping("/admin/event")
public class AdminEventController {

	@Autowired
	private EventService eventService;

	/**
	 * 관리자 이벤트 목록 화면
	 */
	@GetMapping("/eve_list.do")
	public String eventList(SearchDTO param, Model model) {
		param.setDiv("이벤트");

		// 기본 페이지 정보 설정
		if (param.getPageNo() == 0)
			param.setPageNo(1);
		if (param.getPageSize() == 0)
			param.setPageSize(10);

		List<EventDTO> list = eventService.doRetrieve(param);
		int totalCnt = (list != null && !list.isEmpty()) ? list.get(0).getTotalCnt() : 0;

		model.addAttribute("list", list);
		model.addAttribute("search", param);
		model.addAttribute("totalCnt", totalCnt);

		return "admin/event/eve_list";
	}

	/**
	 * 이벤트 등록 화면
	 */
	@GetMapping("/eve_reg.do")
	public String eventReg() {
		return "admin/event/eve_reg";
	}

	/**
	 * 이벤트 등록 처리
	 */
	@PostMapping("/save.do")
	public String save(EventDTO dto, HttpSession session) {
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

		if (loginUser != null) {
			dto.setRegId(loginUser.getId());
			dto.setEmail(loginUser.getEmail());
			dto.setNickname(loginUser.getNickname());
		}

		dto.setDiv("이벤트");
		eventService.doSave(dto);

		return "redirect:/admin/event/eve_list.do";
	}

	/**
	 * 이벤트 수정 화면
	 */
	@GetMapping("/eve_mod.do")
	public String eventMod(@RequestParam("ecode") int ecode, Model model) {
		EventDTO param = new EventDTO();
		param.setEcode(ecode);

		EventDTO out = eventService.doSelectOne(param);
		model.addAttribute("event", out);

		return "admin/event/eve_mod";
	}

	/**
	 * 이벤트 수정 처리
	 */
	@PostMapping("/update.do")
	public String update(EventDTO dto, HttpSession session) {
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

		if (loginUser != null) {
			dto.setModId(loginUser.getId());
		}

		if (dto.getDiv() == null || dto.getDiv().trim().isEmpty()) {
			dto.setDiv("이벤트");
		}

		eventService.doUpdate(dto);

		return "redirect:/admin/event/eve_list.do";
	}

	/**
	 * 이벤트 삭제 처리 (선택 삭제)
	 */
	@PostMapping("/delete.do")
	public String delete(@RequestParam(value = "ecodeList", required = false) List<Integer> ecodeList) {
		if (ecodeList != null && !ecodeList.isEmpty()) {
			for (int code : ecodeList) {
				EventDTO dto = new EventDTO();
				dto.setEcode(code);
				eventService.doDelete(dto);
			}
		}

		return "redirect:/admin/event/eve_list.do";
	}

	@PostConstruct
	public void init() {
		System.out.println(">>> AdminEventController 로드 완료 <<<");
	}
}