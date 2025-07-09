package com.pcwk.ehr.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.repair.RepairResDTO;
import com.pcwk.ehr.repair.RepairResService;

@Controller
@RequestMapping("/admin/repair")
public class RepairResAdminController implements PLog {
	
	@Autowired
	private RepairResService service;
	
	@GetMapping("/list.do")
	public String list(Model model, HttpServletRequest request) throws Exception {

		int pageNo = 1;
		try {
			String pageNoStr = request.getParameter("pageNo");
			if (pageNoStr != null) {
				pageNo = Integer.parseInt(pageNoStr);
				if (pageNo < 1)
					pageNo = 1;
			}
		} catch (NumberFormatException e) {
			pageNo = 1;
		}

		String searchDiv = request.getParameter("searchDiv");
		String searchWord = request.getParameter("searchWord");

		DTO dto = new DTO();
		dto.setPageNum(pageNo);
		dto.setSearchDiv(searchDiv);
		dto.setSearchWord(searchWord);

		// 페이징 계산
		int pageSize = dto.getPageSize(); // 기본 10
		int startRow = (pageNo - 1) * pageSize + 1;
		int endRow = startRow + pageSize - 1;
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);

		// 전체 건수 조회
		int totalCount = service.getTotalCount(dto);
		dto.setTotalCnt(totalCount);

		// 전체 페이지 수 계산
		int totalPages = (int) Math.ceil((double) totalCount / pageSize);

		// 리스트 조회
		List<RepairResDTO> list = service.doRetrievePaging(dto);

		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("searchDiv", searchDiv);
		model.addAttribute("searchWord", searchWord);

		return "admin/repair/list"; // JSP 뷰 이름
	}
}
	


