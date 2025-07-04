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
		log.debug("┌─────────────────────────────────────┐");
		log.debug("│ EventController()                   │");
		log.debug("└─────────────────────────────────────┘");
	}

	@GetMapping(value = "/doRetrieve.do")
	public String doRetrieve(SearchDTO param, Model model) {
		String viewName = "event/event_list"; // /WEB-INF/views/event/event_list.jsp

		log.debug("┌───────────────────────────┐");
		log.debug("│ *doRetrieve()*            │");
		log.debug("└───────────────────────────┘");

		// 기본값 처리
		int pageNo = PcwkString.nvlZero(param.getPageNo(), 1);
		int pageSize = PcwkString.nvlZero(param.getPageSize(), 10);
		String div = PcwkString.nvlString(param.getDiv(), "10");
		String searchDiv = PcwkString.nullToEmpty(param.getSearchDiv());
		String searchWord = PcwkString.nullToEmpty(param.getSearchWord());

		// 파라미터 설정
		param.setPageNo(pageNo);
		param.setPageSize(pageSize);
		param.setDiv(div);
		param.setSearchDiv(searchDiv);
		param.setSearchWord(searchWord);

		log.debug("SearchDTO param: {}", param);

		// 데이터 조회
		List<EventDTO> list = eventService.doRetrieve(param);
		model.addAttribute("list", list);

		// 전체 건수 처리
		int totalCnt = 0;
		if (list != null && !list.isEmpty()) {
			EventDTO totalVO = list.get(0);
			totalCnt = totalVO.getTotalCnt();
		}
		model.addAttribute("totalCnt", totalCnt);

		return viewName;
	}

	@GetMapping(value = "/doSelectOne.do")
	public String doSelectOne(EventDTO param, Model model) {
		String viewName = "event/event_mod"; // /WEB-INF/views/event/event_mod.jsp

		log.debug("1. param: {}", param);

		EventDTO outVO = eventService.doSelectOne(param);
		log.debug("2. outVO: {}", outVO);

		model.addAttribute("vo", outVO); // 조회된 데이터를 "vo"라는 이름으로 JSP에 전달

		return viewName;
	}

	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(EventDTO param, HttpServletRequest req) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doUpdate()*              │");
		log.debug("└───────────────────────────┘");

		log.debug("1. param: {}", param);

		int flag = eventService.doUpdate(param);
		String message = "";
		if (1 == flag) {
			message = "회원님의 이벤트가 수정되었습니다.";
		} else {
			message = "회원님의 이벤트가 수정되지 않았습니다.";
		}

		String jsonString = new Gson().toJson(new MessageDTO(flag, message));
		log.debug("2. jsonString: {}", jsonString);

		return jsonString;
	}

	// 삭제 /event/doDelete.do
	@PostMapping(value = "/doDelete.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(EventDTO param, HttpServletRequest req) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doDelete()*              │");
		log.debug("└───────────────────────────┘");

		log.debug("1. param: {}", param);

		int flag = eventService.doDelete(param);

		String message = "";
		if (flag == 1) {
			message = "이벤트가 삭제 되었습니다.";
		} else {
			message = "이벤트 삭제에 실패했습니다.";
		}

		MessageDTO messageDTO = new MessageDTO(flag, message);
		String jsonString = new Gson().toJson(messageDTO);

		log.debug("2. jsonString: {}", jsonString);

		return jsonString;
	}

	// 등록 요청 처리 : /event/doSave.do (POST, JSON 반환)
	@PostMapping(value = "/doSave.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(EventDTO param) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSave()*                │");
		log.debug("└───────────────────────────┘");

		log.debug("1. param: {}", param);

		int flag = eventService.doSave(param);

		String message = "";
		if (1 == flag) {
			message = param.getTitle() + " 글이 등록되었습니다.";
		} else {
			message = param.getTitle() + " 글이 등록되지 않았습니다.";
		}

		MessageDTO messageDTO = new MessageDTO(flag, message);
		String jsonString = new Gson().toJson(messageDTO);

		log.debug("2. jsonString: {}", jsonString);

		return jsonString;
	}
}