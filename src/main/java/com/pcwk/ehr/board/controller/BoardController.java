package com.pcwk.ehr.board.controller;

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
import com.pcwk.ehr.board.BoardService;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;

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
		String viewName = "board/board_list";
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doRetrieve()*            │");
		log.debug("└───────────────────────────┘");
		// 기본값 처리
		int pageNo = PcwkString.nvlZero(param.getPageNo(), 1);
		int pageSize = PcwkString.nvlZero(param.getPageSize(), 10);
		String div = PcwkString.nvlString(param.getDiv(), "10");
		String searchDiv = PcwkString.nullToEmpty(param.getSearchDiv());
		String searchWord = PcwkString.nullToEmpty(param.getSearchWord());
		log.debug("pageNo:{}", pageNo);
		log.debug("pageSize:{}", pageSize);
		log.debug("div:{}", div);
		log.debug("searchDiv:{}", searchDiv);
		log.debug("searchWord:{}", searchWord);

		// 파라미터 설정
		param.setPageNo(pageNo);
		param.setPageSize(pageSize);
		param.setDiv(div);
		param.setSearchDiv(searchDiv);
		param.setSearchWord(searchWord);
		log.debug("***param:{}", param);
		// 데이터 조회
		List<BoardDTO> list = boardService.doRetrieve(param);
		model.addAttribute("list", list);
		int totalCnt = 0;
		if (list != null && list.size() > 0) {
			BoardDTO totalVO = list.get(0);
			totalCnt = totalVO.getTotalCnt();
		}
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("divValue", div);
		// Form Submit: 파라메터 값 유지
		model.addAttribute("search", param);
		return viewName;
	}

	@GetMapping(value = "/doSelectOne.do")
	public String doSelectOne(BoardDTO param, Model model) {
		String viewName = "board/board_mod"; // /WEB-INF/views/board/board_mod.jsp
		log.debug("1. param: {}", param);

		// 게시구분: 공지사항(10)
		String div = PcwkString.nvlString(param.getDiv(), "10");
		log.debug("2. div: {}", div);

		BoardDTO outVO = boardService.doSelectOne(param);
		log.debug("2. outVO: {}", outVO);
		model.addAttribute("vo", outVO); // 조회된 데이터를 "vo"라는 이름으로 JSP에 전달
		model.addAttribute("divValue", div);

		return viewName;
	}

	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(BoardDTO param, HttpServletRequest req) {
	    log.debug("┌───────────────────────────┐");
	    log.debug("│ *doUpdate()*              │");
	    log.debug("└───────────────────────────┘");
	    log.debug("1. 요청 param: {}", param);

	    // 로그인 사용자 정보 가져오기
	    Object loginUserObj = req.getSession().getAttribute("loginUser");
	    if (loginUserObj == null) {
	        return new Gson().toJson(new MessageDTO(0, "로그인이 필요합니다."));
	    }

	    // 로그인 사용자 ID
	    String loginUserId = ((com.pcwk.ehr.member.MemberDTO) loginUserObj).getId();
	    log.debug("2. 로그인 사용자 ID: {}", loginUserId);

	    // 원본 글 조회
	    BoardDTO original = boardService.doSelectOne(param);
	    if (original == null) {
	        return new Gson().toJson(new MessageDTO(0, "해당 게시글을 찾을 수 없습니다."));
	    }

	    // 권한 확인: 작성자 또는 admin만 수정 가능
	    if (!loginUserId.equals(original.getRegId()) && !"admin".equals(loginUserId)) {
	        return new Gson().toJson(new MessageDTO(0, "수정 권한이 없습니다."));
	    }

	    // 최종 수정자(modId) 설정
	    param.setModId(loginUserId);

	    int flag = boardService.doUpdate(param);
	    String message = (flag == 1) ? "회원님의 글이 수정되었습니다." : "회원님의 글이 수정되지 않았습니다.";
	    return new Gson().toJson(new MessageDTO(flag, message));
	}

	// 삭제 /board/doDelete.do doDelete(BoardDTO param) 비동기 POST JSON
	@PostMapping(value = "/doDelete.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(BoardDTO param, HttpServletRequest req) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doDelete()*              │");
		log.debug("└───────────────────────────┘");
		log.debug("1. param:{}", param);
		String jsonString = "";
		int flag = boardService.doDelete(param);
		String message = "";
		if (1 == flag) {
			message = "게시판 글이 삭제 되었습니다.";
		} else {
			message = "게시판 글이 삭제 되지않았습니다.";
		}
		// MessageDTO messageDTO = new MessageDTO(flag, message); // :흰색_확인_표시: 메시지 객체
		// 생성
		// String jsonString = new Gson().toJson(messageDTO); // :흰색_확인_표시: JSON 문자열로 변환
		// log.debug("2. jsonString: {}", jsonString);
		// return jsonString;
		jsonString = new Gson().toJson(new MessageDTO(flag, message));
		log.debug("2. jsonString:{}", jsonString);
		return jsonString;
	}

	// 화면등록 : /board/board_list
	// 등록 : /board/board_reg
	@PostMapping(value = "/doSave.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(BoardDTO param, HttpServletRequest req) {
	    log.debug("┌───────────────────────────┐");
	    log.debug("│ *doSave()*                │");
	    log.debug("└───────────────────────────┘");
	    log.debug("1. param:{}", param);

	    // ✅ 로그인 유저 정보에서 nickname과 id 세팅
	    com.pcwk.ehr.member.MemberDTO loginUser =
	        (com.pcwk.ehr.member.MemberDTO) req.getSession().getAttribute("loginUser");

	    if (loginUser != null) {
	        param.setNickname(loginUser.getNickname()); // 👈 작성자 이름(닉네임)
	        param.setId(loginUser.getId());             // 👈 작성자 ID
	        param.setRegId(loginUser.getId());          // 👈 등록자 ID
	    }

	    int flag = boardService.doSave(param);

	    String message = (flag == 1)
	        ? param.getTitle() + " 글이 등록되었습니다."
	        : param.getTitle() + " 글이 등록되지 않았습니다.";

	    String jsonString = new Gson().toJson(new MessageDTO(flag, message));
	    log.debug("2. jsonString:{}", jsonString);
	    return jsonString;
	}
}