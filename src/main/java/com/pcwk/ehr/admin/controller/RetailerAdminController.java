package com.pcwk.ehr.admin.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.retailer.RetailerDTO;
import com.pcwk.ehr.retailer.service.RetailerService;

@Controller
@RequestMapping("admin/retailer")
public class RetailerAdminController implements PLog {

	@Autowired
	RetailerService service;

	@GetMapping("/list.do")
	public String list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "searchType", required = false, defaultValue = "productName") String searchType,
			Model model) {

		log.debug("retailerList() - 관리자 리테일러 목록 조회");

		if (pageNum < 1)
			pageNum = 1;

		int totalCount = 0;
		List<RetailerDTO> retailerList = null;

		if (searchWord == null || searchWord.trim().isEmpty()) {
			// 검색어 없으면 기존 방식으로 전체 목록
			totalCount = service.getRetailerCount();
			retailerList = service.getRetailersByPage(pageNum, pageSize);
		} else {
			// 검색어 있을 때는 검색 조건 반영
			totalCount = service.getRetailersCountWithSearch(searchType, searchWord);
			retailerList = service.getRetailersByPageWithSearch(pageNum, pageSize, searchType, searchWord);
		}

		int totalPages = (int) Math.ceil((double) totalCount / pageSize);

		if (pageNum > totalPages)
			pageNum = totalPages > 0 ? totalPages : 1;

		model.addAttribute("retailerList", retailerList);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchWord", searchWord);

		return "admin/retailer/list";
	}

	@GetMapping("/add.do")
	public String addRetailerView(Model model) {
		model.addAttribute("retailer", new RetailerDTO());
		return "admin/retailer/addRetailer";
	}

	@PostMapping("/add.do")
	public String addRetailer(@ModelAttribute RetailerDTO retailer, HttpSession session) {

		 Date sqlDate = Date.valueOf(LocalDate.now());

		// 로그인 사용자 닉네임 가져오기
		String nickname = (String) session.getAttribute("nickname");
		if (nickname == null) {
			nickname = "anonymous"; // 로그인 안 되어 있을 경우 대비
		}

		// 날짜 및 작성자 정보 세팅
		retailer.setRegDt(sqlDate);
		retailer.setModDt(sqlDate);
		retailer.setRegId(nickname);
		retailer.setModId(nickname);

		int flag = service.doSave(retailer);
		if (flag > 0) {
			log.debug("리테일러 등록 성공: " + retailer);
		} else {
			log.debug("리테일러 등록 실패");
		}
		return "redirect:/admin/main.do";
	}
	
	@PostMapping("/delete.do")
	public String delete(@RequestParam("retailerCode") int retailerCode, RedirectAttributes rttr) {
		log.debug("┌─────────────────────────────┐");
		log.debug("│ delete() - 차량 정보 삭제 처리 (retailerCode={}) │", retailerCode);
		log.debug("└─────────────────────────────┘");
		
	    RetailerDTO dto = new RetailerDTO();
	    dto.setRetailerCode(retailerCode);

	    int flag = service.doDelete(dto);

	    if (flag > 0) {
	        log.debug("리테일러 삭제 성공: " + retailerCode);
	    } else {
	        log.debug("리테일러 삭제 실패: " + retailerCode);
	    }
		
		
		return "redirect:/admin/retailer/list.do";		
	}
	
	@GetMapping("/updateView.do")
	public String updateView(@RequestParam("retailerCode") int retailerCode, Model model) {
		
		RetailerDTO retailer = service.getOne(retailerCode);
	    model.addAttribute("retailer", retailer);	
		return "admin/retailer/updateRetailer";
	}
	
	@PostMapping("/update.do")
	public String update(@ModelAttribute RetailerDTO retailer, HttpSession session) {
		Date sqlDate = Date.valueOf(LocalDate.now());

		// 로그인 사용자 닉네임 가져오기
		String nickname = (String) session.getAttribute("nickname");
		if (nickname == null) {
			nickname = "anonymous"; // 로그인 안 되어 있을 경우 대비
		}
		
		retailer.setModDt(sqlDate);
		retailer.setModId(nickname);
		
		int flag = service.doUpdate(retailer);
		
		if (flag > 0) {
			log.debug("리테일러 수정 성공: " + retailer);
		} else {
			log.debug("리테일러 수정 실패");
		}
		
		
		return "redirect:/admin/main.do";	
	}
	
	

}
