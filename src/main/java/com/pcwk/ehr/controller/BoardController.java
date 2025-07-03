package com.pcwk.ehr.board.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.board.BoardService;
import com.pcwk.ehr.cmn.MessageDTO;

@Controller
@RequestMapping("/board")
public class BoardController {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	BoardService boardService;

	public BoardController() {
		log.debug("┌─────────────────────────────────────┐");
		log.debug("│ BoardController()                   │");
		log.debug("└─────────────────────────────────────┘");

	}

	// 화면등록 : /board/board_list
	// 등록 : /board/board_reg
	@PostMapping(value = "/doSave.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(BoardDTO param) {
		// 등록 /board/doSave.do doSave(BoardDTO param) 비동기 POST JSON
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSave()*                │");
		log.debug("└───────────────────────────┘");
		String jsonString = "";
		log.debug("1. param:{}", param);

		int flag = boardService.doSave(param);
		String message = "";

		if (1 == flag) {
			message = param.getTitle() + " 글이 등록되었습니다.";
		} else {
			message = param.getTitle() + " 글이 등록되지 않았습니다.";
		}

		MessageDTO messageDTO = new MessageDTO(flag, message);
		jsonString = new Gson().toJson(messageDTO);
		log.debug("2. jsonString:{}", jsonString);

		return jsonString;
	}

}
