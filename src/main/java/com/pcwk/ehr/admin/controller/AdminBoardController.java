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

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.board.BoardService;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.member.MemberDTO;

@Controller
@RequestMapping("/admin/board")
public class AdminBoardController {

	@Autowired
	private BoardService boardService;

	/**
	 * 관리자 게시판 목록 조회
	 */
	@GetMapping("/boa_list.do")
	public String boardList(SearchDTO param, Model model) {
		if (param.getPageNo() == 0)
			param.setPageNo(1);
		if (param.getPageSize() == 0)
			param.setPageSize(10);
		if (param.getDiv() == null || param.getDiv().isEmpty())
			param.setDiv("10"); // 자유게시판 기본값

		List<BoardDTO> list = boardService.doRetrieve(param);
		int totalCnt = (list != null && !list.isEmpty()) ? list.get(0).getTotalCnt() : 0;

		model.addAttribute("list", list);
		model.addAttribute("search", param);
		model.addAttribute("totalCnt", totalCnt);

		return "admin/board/boa_list";
	}

	/**
	 * 게시글 등록 화면
	 */
	@GetMapping("/boa_reg.do")
	public String boardReg() {
		return "admin/board/boa_reg";
	}

	/**
	 * 게시글 수정 화면
	 */
	@GetMapping("/boa_mod.do")
	public String boardMod(@RequestParam("boardCode") int boardCode, Model model) {
		BoardDTO param = new BoardDTO();
		param.setBoardCode(boardCode);

		BoardDTO board = boardService.doSelectOne(param);
		model.addAttribute("board", board); // JSP에서 ${board.title} 사용

		return "admin/board/boa_mod";
	}

	/**
	 * 게시글 상세 조회 (읽기 전용)
	 */
	@GetMapping("/boa_detail.do") // JSP와 정확히 일치시켜야 함
	public String boardDetail(@RequestParam("boardCode") int boardCode, Model model) {
	    BoardDTO dto = new BoardDTO();
	    dto.setBoardCode(boardCode);
	    
	    BoardDTO out = boardService.doSelectOne(dto);
	    model.addAttribute("vo", out);

	    return "admin/board/boa_detail"; // 이 JSP가 존재해야 합니다.
	}
	/**
	 * 게시글 등록 처리
	 */
	@PostMapping("/save.do")
	public String save(BoardDTO dto, HttpSession session) {
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

		if (loginUser != null) {
			dto.setRegId(loginUser.getId());
			dto.setNickname(loginUser.getNickname());
			dto.setId(loginUser.getId());
		}

		if (dto.getDiv() == null || dto.getDiv().isEmpty()) {
			dto.setDiv("10"); // 자유게시판 기본값
		}

		boardService.doSave(dto);

		return "redirect:/admin/board/boa_list.do";
	}

	/**
	 * 게시글 수정 처리
	 */
	@PostMapping("/update.do")
	public String update(BoardDTO dto, HttpSession session) {
	    MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

	    if (loginUser != null) {
	        dto.setModId(loginUser.getId());
	        dto.setId(loginUser.getId()); // ★ 이 줄이 반드시 있어야 합니다
	    } else {
	        // 로그인 안된 상태일 경우 기본 ID라도 넣거나 오류 처리 필요
	        dto.setModId("admin");
	        dto.setId("admin");
	    }

	    if (dto.getDiv() == null || dto.getDiv().trim().isEmpty()) {
	        dto.setDiv("10");
	    }

	    boardService.doUpdate(dto);

	    return "redirect:/admin/board/boa_list.do";
	}

	/**
	 * 게시글 삭제 처리 (선택 삭제)
	 */
	@PostMapping("/deleteBoards.do")
	public String delete(@RequestParam(value = "boardCodeList", required = false) List<Integer> boardCodeList) {
		if (boardCodeList != null && !boardCodeList.isEmpty()) {
			for (int code : boardCodeList) {
				BoardDTO dto = new BoardDTO();
				dto.setBoardCode(code);
				boardService.doDelete(dto);
			}
		}

		return "redirect:/admin/board/boa_list.do";
	}

	@PostConstruct
	public void init() {
		System.out.println(">>> AdminBoardController 로드 완료 <<<");
	}
}