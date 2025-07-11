package com.pcwk.ehr.retailer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.retailer.service.RetailerService;

@Controller
@RequestMapping("/retailer")
public class RetailerController implements PLog {

    @Autowired
    private RetailerService retailerService;

    // 1. 전체 리테일러 목록 조회 (페이징)
    @GetMapping("/all.do")
    public String list(
        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
        @RequestParam(value = "searchType", required = false, defaultValue = "retailerName") String searchType,
        @RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
        Model model
    ) {
        int totalCount = retailerService.getRetailersCountWithSearch(searchType, searchWord);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // pageNum 범위 보정
        if(totalPages > 0 && pageNum > totalPages) {
            pageNum = totalPages;
        }
        if(pageNum < 1) {
            pageNum = 1;
        }

        List<RetailerDTO> retailerList = retailerService.getRetailersByPageWithSearch(pageNum, pageSize, searchType, searchWord);

        model.addAttribute("retailerList", retailerList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("totalPages", totalPages);

        return "retailer/all";
    }



}
