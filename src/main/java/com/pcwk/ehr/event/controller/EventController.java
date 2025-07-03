package com.pcwk.ehr.event.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
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