package com.pcwk.ehr.retailer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.retailer.service.RetailerService;
@Controller
@RequestMapping("/retailer")
public class RetailerController implements PLog{
	 @Autowired
	    private RetailerService retailerService;
	 private int retailerCode;
	    // 1. 전체 리테일러 목록(페이징) 조회
	    @GetMapping("/list.do")
	    public String list(
	        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
	        Model model
	    ) {
	        log.debug("┌──────────────────────────────────────┐");
	        log.debug("│ list() - 전체 리테일러 목록 조회(페이징) │");
	        log.debug("└──────────────────────────────────────┘");

	        int totalCount = retailerService.getRetailerCount();
	        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

	        List<RetailerDTO> retailerList = retailerService.getRetailersByPage(pageNum, pageSize);

	        model.addAttribute("retailerList", retailerList);
	        model.addAttribute("currentPage", pageNum);
	        model.addAttribute("pageSize", pageSize);
	        model.addAttribute("totalPages", totalPages);
	        model.addAttribute("totalCount", totalCount);

	        return "retailer/list";
	    }

	    // 2. 리테일러 목록 조회 (전체, 페이징 없이)
	    @GetMapping("/all.do")
	    public String allList(Model model) {
	        log.debug("┌───────────────────────────────┐");
	        log.debug("│ allList() - 리테일러 전체 목록 조회 │");
	        log.debug("└───────────────────────────────┘");

	        List<RetailerDTO> retailerList = retailerService.getAllRetailers();
	        model.addAttribute("retailerList", retailerList);
	        return "retailer/all";
	    }

	    // 3. 리테일러 상세 정보 조회
	    @GetMapping("/detail")
	    public String detail(@RequestParam("retailerCode") int retailerCode, Model model) {
	        log.debug("┌──────────────────────────────────────────────┐");
	        log.debug("│ detail() - 리테일러 상세 정보 조회 (retailerId={}) │", retailerCode);
	        log.debug("└──────────────────────────────────────────────┘");

	        RetailerDTO dto = retailerService.getOne(retailerCode);
	        model.addAttribute("retailer", dto);  // ← 이 코드가 없으면 에러 발생
	        return "retailer/detail";
	    }
	}