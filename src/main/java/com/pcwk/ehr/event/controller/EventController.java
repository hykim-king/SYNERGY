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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.event.EventDTO;
import com.pcwk.ehr.event.EventService;

@Controller
@RequestMapping("/event")
public class EventController {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	EventService eventService;

	public EventController() {
		log.debug("┌─────────────────────────────────────┐");
		log.debug("│ EventController()                   │");
		log.debug("└─────────────────────────────────────┘");
	}

	// 등록 화면 조회: /board/doSaveView.do
	@GetMapping("/doSaveView.do")
	public String doSaveView(@RequestParam(name = "div", defaultValue = "10") String div, Model model) {
		String viewNString = "board/board_reg"; // -> /WEB-INF/views/board/board_reg.jsp
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSaveView()*            │");
		log.debug("└───────────────────────────┘");
		log.debug("div: {}", div);

		model.addAttribute("board_div", div); // JSP에서 ${board_div}로 사용 가능

		log.debug("viewNString: {}", viewNString);

		return viewNString;
	}

	@GetMapping(value = "/doRetrieve.do")
	public String doRetrieve(SearchDTO param, Model model) {
	    String viewName = "event/event_list"; // /WEB-INF/views/event/event_list.jsp

	    log.debug("┌──────────── doRetrieve() ────────────┐");

	    // 1. 기본값 처리
	    int pageNo = PcwkString.nvlZero(param.getPageNo(), 1);
	    int pageSize = PcwkString.nvlZero(param.getPageSize(), 10);
	    String div = PcwkString.nvlString(param.getDiv(), "10");
	    String searchDiv = PcwkString.nullToEmpty(param.getSearchDiv());
	    String searchWord = PcwkString.nullToEmpty(param.getSearchWord());

	    // 2. 파라미터 설정
	    param.setPageNo(pageNo);
	    param.setPageSize(pageSize);
	    param.setDiv(div);
	    param.setSearchDiv(searchDiv);
	    param.setSearchWord(searchWord);

	    log.debug("pageNo      : {}", pageNo);
	    log.debug("pageSize    : {}", pageSize);
	    log.debug("div         : {}", div);
	    log.debug("searchDiv   : {}", searchDiv);
	    log.debug("searchWord  : {}", searchWord);
	    log.debug("param       : {}", param);

	    // 3. 데이터 조회
	    List<EventDTO> list = eventService.doRetrieve(param);
	    model.addAttribute("list", list);

	    // 4. 총 건수 추출 (첫 row에서)
	    int totalCnt = 0;
	    if (list != null && !list.isEmpty()) {
	        totalCnt = list.get(0).getTotalCnt();
	    }

	    // 5. View 전달값 설정
	    model.addAttribute("totalCnt", totalCnt);
	    model.addAttribute("divValue", div);
	    model.addAttribute("search", param);

	    log.debug("totalCnt    : {}", totalCnt);
	    log.debug("└─────────────────────────────────────┘");

	    return viewName;
	}

	@GetMapping(value = "/doSelectOne.do")
	public String doSelectOne(EventDTO param, Model model) {
		String viewName = "event/event_mod"; // /WEB-INF/views/event/event_mod.jsp

		log.debug("┌──────────────────────────────┐");
		log.debug("│ doSelectOne - param: {}      │", param);
		log.debug("└──────────────────────────────┘");

		// 1. ecode 유효성 검사
		if (param.getEcode() == null || param.getEcode().trim().isEmpty()) {
			// 잘못된 요청 처리 (간단히 예외 발생 / 혹은 에러 페이지 리다이렉트도 가능)
			throw new IllegalArgumentException("이벤트 코드(ecode)는 필수입니다.");
		}

		// 2. 서비스 호출
		EventDTO outVO = eventService.doSelectOne(param);
		log.debug("조회 결과 outVO: {}", outVO);

		// 3. 조회 결과 null 체크
		if (outVO == null) {
			// 예외 발생 또는 오류 페이지로 이동 가능
			throw new IllegalStateException("조회된 이벤트가 없습니다. ecode=" + param.getEcode());
		}

		// 4. 모델에 데이터 담기
		model.addAttribute("vo", outVO);

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