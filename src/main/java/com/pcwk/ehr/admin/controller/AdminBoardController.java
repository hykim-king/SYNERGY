package com.pcwk.ehr.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.board.BoardService;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.member.MemberDTO;

@Controller
@RequestMapping("/admin/board")
public class AdminBoardController {

	final Logger LOG = LogManager.getLogger(getClass());

	@Autowired
	BoardService boardService;

	// 관리자 게시판 목록
	@GetMapping("/boa_list.do")
	public String boardList(SearchDTO param, HttpSession session, Model model) {
		LOG.debug("[boardList] param: {}", param);

		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
		if (loginUser == null || loginUser.getAdminRole() != 1) {
			model.addAttribute("errorMessage", "관리자 권한이 필요합니다.");
			return "common/error";
		}

		// 페이지 기본값 설정
		param.setPageNo(param.getPageNo() == 0 ? 1 : param.getPageNo());
		param.setPageSize(param.getPageSize() == 0 ? 10 : param.getPageSize());

		List<BoardDTO> list = boardService.doRetrieve(param);
		int totalCnt = 0;
		if (list != null && !list.isEmpty()) {
			totalCnt = list.get(0).getTotalCnt();
		}

		model.addAttribute("list", list);
		model.addAttribute("search", param);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("divValue", param.getDiv());

		return "admin/board/boa_list"; // ✅ JSP 경로로 리턴
	}

	// 게시글 수정 화면 진입
	@GetMapping("/boardMod.do")
	public String boardMod(int boardCode, HttpSession session, Model model) {
		LOG.debug("[boardMod] boardCode: {}", boardCode);

		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
		if (loginUser == null || loginUser.getAdminRole() != 1) {
			model.addAttribute("errorMessage", "관리자 권한이 필요합니다.");
			return "common/error";
		}

		BoardDTO param = new BoardDTO();
		param.setBoardCode(boardCode);
		BoardDTO board = boardService.doSelectOne(param);

		if (board == null) {
			model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
			return "common/error";
		}

		model.addAttribute("board", board);
		return "admin/board/boa_mod"; // 수정 화면
	}

	// 게시글 수정 처리
	@PostMapping("/updateBoard.do")
	public String updateBoard(BoardDTO board, HttpSession session, Model model) {
	    LOG.debug("[updateBoard] board: {}", board);

	    MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
	    if (loginUser == null || loginUser.getAdminRole() != 1) {
	        model.addAttribute("errorMessage", "관리자 권한이 필요합니다.");
	        return "common/error";
	    }

	    board.setModId(loginUser.getId());

	    int result = boardService.doUpdate(board);

	    if (result > 0) {
	        return "redirect:/admin/board/boa_list.do";
	    } else {
	        model.addAttribute("errorMessage", "게시글 수정에 실패했습니다.");
	        return "common/error";
	    }
	}

	// 관리자 다중 삭제 처리
	@PostMapping(value = "/deleteBoards.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteBoards(@RequestBody List<Integer> codes, HttpSession session) {
	    LOG.debug("[deleteBoards] codes: {}", codes);

	    MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
	    if (loginUser == null || loginUser.getAdminRole() != 1) {
	        return new Gson().toJson(new MessageDTO(0, "관리자 권한이 필요합니다."));
	    }

	    int deleteCount = 0;
	    for (int boardCode : codes) {
	        BoardDTO param = new BoardDTO();
	        param.setBoardCode(boardCode);
	        deleteCount += boardService.doDelete(param);
	    }

	    String message = (deleteCount == codes.size()) ? "정상적으로 삭제되었습니다." : "일부 항목 삭제 실패.";
	    return new Gson().toJson(new MessageDTO(deleteCount, message));
	}
}