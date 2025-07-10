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

@Controller
@RequestMapping("/event")
public class EventController {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	EventService eventService;

	public EventController() {
		log.debug("EventController() 생성");
	}

	// 등록 화면 이동 (관리자만)
	@GetMapping("/doSaveView.do")
	public String doSaveView(HttpServletRequest req) {
		String loginUserId = (String) req.getSession().getAttribute("loginUserId");
		if (!"admin".equals(loginUserId)) {
			return "redirect:/event/doRetrieve.do";
		}
		return "event/event_reg";
	}

	// 이벤트 목록 조회
	@GetMapping("/doRetrieve.do")
	public String doRetrieve(SearchDTO param, Model model) {
		// 기본값 설정
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

	// 단건 조회
	@GetMapping("/doSelectOne.do")
	public String doSelectOne(EventDTO param, Model model, HttpServletRequest req) {
	    String loginUserId = (String) req.getSession().getAttribute("loginUserId");

	    // ★ 조회자 ID 세팅 (조회수 증가 조건 충족을 위해 필수)
	    if (loginUserId != null) {
	        param.setRegId(loginUserId);
	    }

	    EventDTO outVO = eventService.doSelectOne(param);
	    if (outVO == null) {
	        model.addAttribute("errorMessage", "조회된 이벤트가 없습니다.");
	        return "common/error";
	    }

	    model.addAttribute("vo", outVO);
	    return "event/event_mod";
	}

	// 이벤트 수정 (관리자 또는 작성자만 가능)
	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(EventDTO param, HttpServletRequest req) {
		String loginUserId = (String) req.getSession().getAttribute("loginUserId");

		if (loginUserId == null || (!"admin".equals(loginUserId) && !loginUserId.equals(param.getRegId()))) {
			return new Gson().toJson(new MessageDTO(0, "수정 권한이 없습니다."));
		}

		int flag = eventService.doUpdate(param);
		String message = (flag == 1) ? "이벤트가 수정되었습니다." : "이벤트 수정 실패";
		return new Gson().toJson(new MessageDTO(flag, message));
	}

	// 이벤트 삭제 (관리자만)
	@PostMapping(value = "/doDelete.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(EventDTO param, HttpServletRequest req) {
	    String loginUserId = (String) req.getSession().getAttribute("loginUserId");

	    // 1. 관리자 권한 체크
	    if (!"admin".equals(loginUserId)) {
	        return new Gson().toJson(new MessageDTO(0, "삭제 권한이 없습니다."));
	    }

	    // 2. 삭제 수행
	    int flag = eventService.doDelete(param);

	    // 3. 결과 메시지 생성 및 반환
	    String message = (flag == 1) ? "이벤트가 삭제되었습니다." : "이벤트 삭제 실패";
	    return new Gson().toJson(new MessageDTO(flag, message));
	}

	// 이벤트 등록 (관리자만)
	@PostMapping(value = "/doSave.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(EventDTO param, HttpServletRequest req) {
		String loginUserId = (String) req.getSession().getAttribute("loginUserId");

		if (!"admin".equals(loginUserId)) {
			return new Gson().toJson(new MessageDTO(0, "관리자만 등록할 수 있습니다."));
		}

		int flag = eventService.doSave(param);
		String message = (flag == 1) ? param.getTitle() + "Carpick글이 등록되었습니다." : param.getTitle() + " 이벤트 등록 실패";
		return new Gson().toJson(new MessageDTO(flag, message));
	}
}