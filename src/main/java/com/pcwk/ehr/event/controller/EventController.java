package com.pcwk.ehr.event.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.event.EventDTO;
import com.pcwk.ehr.event.EventService;
import com.pcwk.ehr.member.MemberDTO;

@Controller
@RequestMapping("/event")
public class EventController {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	EventService eventService;

	public EventController() {
		log.debug("┌──────────────────────────────────────┐");
		log.debug("│ EventController() 생성자 호출됨     │");
		log.debug("└──────────────────────────────────────┘");
	}

	// 등록 화면 이동 (관리자만)
	@GetMapping("/doSaveView.do")
	public String doSaveView(EventDTO param, Model model, HttpServletRequest req) {
	    MemberDTO loginUser = (MemberDTO) req.getSession().getAttribute("loginUser");

	    if (loginUser == null || !"admin".equals(loginUser.getId())) {
	        return "redirect:/event/doRetrieve.do";
	    }

	    if (param.getEcode() != 0) {
	        EventDTO eventVO = eventService.doSelectOne(param);
	        model.addAttribute("vo", eventVO);
	    }

	    return "event/event_reg";
	}

	// 이벤트 목록 조회
	@GetMapping("/doRetrieve.do")
	public String doRetrieve(SearchDTO param, Model model) {
		param.setPageNo(PcwkString.nvlZero(param.getPageNo(), 1));
		param.setPageSize(PcwkString.nvlZero(param.getPageSize(), 10));
		param.setDiv(PcwkString.nvlString(param.getDiv(), "10"));
		param.setSearchDiv(PcwkString.nullToEmpty(param.getSearchDiv()));
		param.setSearchWord(PcwkString.nullToEmpty(param.getSearchWord()));

		List<EventDTO> list = eventService.doRetrieve(param);
		int totalCnt = (list != null && !list.isEmpty()) ? list.get(0).getTotalCnt() : 0;

		model.addAttribute("list", list);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("divValue", param.getDiv());
		model.addAttribute("search", param);

		return "event/event_list";
	}

	// 단건 조회 (관리자는 수정화면, 일반회원은 상세화면)
	@GetMapping("/doSelectOne.do")
	public String doSelectOne(EventDTO param, Model model, HttpServletRequest req) {
	    // 조회수 증가 (선택적)
	    eventService.updateReadCnt(param);

	    EventDTO outVO = eventService.doSelectOne(param);
	    if (outVO == null) return "redirect:/event/doRetrieve.do";

	    model.addAttribute("vo", outVO);
	    return "event/event_mod";
	}

	// 이벤트 등록 (관리자만)
	@PostMapping(value = "/doSave.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(EventDTO param, HttpServletRequest req) {
		MemberDTO loginUser = (MemberDTO) req.getSession().getAttribute("loginUser");

		if (loginUser == null || !"admin".equals(loginUser.getId())) {
			return new Gson().toJson(new MessageDTO(0, "관리자만 등록할 수 있습니다."));
		}

		int flag = eventService.doSave(param);
		String message = (flag == 1) ? param.getTitle() + " CarPick 이벤트가 등록되었습니다." : param.getTitle() + " 이벤트 등록 실패";

		log.debug("▶ doSave result: {}", message);
		return new Gson().toJson(new MessageDTO(flag, message));
	}

	// 이벤트 수정 (관리자 또는 작성자)
	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(EventDTO param, HttpServletRequest req) {
		MemberDTO loginUser = (MemberDTO) req.getSession().getAttribute("loginUser");

		if (loginUser == null || (!"admin".equals(loginUser.getId()) && !loginUser.getId().equals(param.getRegId()))) {
			return new Gson().toJson(new MessageDTO(0, "수정 권한이 없습니다."));
		}

		int flag = eventService.doUpdate(param);
		String message = (flag == 1) ? "이벤트가 수정되었습니다." : "이벤트 수정 실패";

		log.debug("▶ doUpdate result: {}", message);
		return new Gson().toJson(new MessageDTO(flag, message));
	}

	// 이벤트 삭제 (관리자 또는 작성자)
	@PostMapping(value = "/doDelete.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(EventDTO param, HttpServletRequest req) {
		MemberDTO loginUser = (MemberDTO) req.getSession().getAttribute("loginUser");

		if (loginUser == null || (!"admin".equals(loginUser.getId()) && !loginUser.getId().equals(param.getRegId()))) {
			return new Gson().toJson(new MessageDTO(0, "삭제 권한이 없습니다."));
		}

		int flag = eventService.doDelete(param);
		String message = (flag == 1) ? "이벤트가 삭제되었습니다." : "이벤트 삭제 실패";

		log.debug("▶ doDelete result: {}", message);
		return new Gson().toJson(new MessageDTO(flag, message));
	}
}
