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
        // 전체 건수는 'getCarCountWithSearch' 사용
        int totalCount = retailerService.getRetailersCountWithSearch(searchType, searchWord);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 실제 데이터 목록은 'getCarsByPageWithSearch' 사용
        List<RetailerDTO> retailerList = retailerService.getRetailersByPageWithSearch(pageNum, pageSize, searchType, searchWord);

        model.addAttribute("retailerList", retailerList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("totalPages", totalPages);

        return "retailer/all";
    }



//    // 2. 전체 리테일러 목록 조회 (페이징 없이)
//    @GetMapping("/all.do")
//    public String allList(Model model) {
//        log.debug("allList() - 리테일러 전체 목록 조회");
//        List<RetailerDTO> list = retailerService.getAllRetailers();
//        model.addAttribute("retailerList", list);
//        return "retailer/all";
//    }

    // 3. 리테일러 상세 조회
    @GetMapping("/detail.do")
    public String detail(@RequestParam("retailerCode") int retailerCode, Model model) {
        log.debug("detail() - 리테일러 상세 조회 (retailerCode={})", retailerCode);
        RetailerDTO dto = retailerService.getOne(retailerCode);
        model.addAttribute("retailer", dto);
        return "retailer/detail";
    }

    // 4. 리테일러 등록 폼
    @GetMapping("/reg.do")
    public String registerForm() {
        log.debug("registerForm() - 리테일러 등록 폼 호출");
        return "retailer/reg";
    }

    // 5. 리테일러 등록 처리
    @PostMapping("/save.do")
    public String save(RetailerDTO dto, RedirectAttributes rttr) {
        log.debug("save() - 리테일러 등록 처리 (dto={})", dto);
        int flag = retailerService.doSave(dto);
        rttr.addFlashAttribute("msg", flag == 1 ? "리테일러가 등록되었습니다." : "리테일러 등록에 실패했습니다.");
        return "redirect:/retailer/list.do";
    }

    // 6. 리테일러 수정 폼
    @GetMapping("/mod.do")
    public String modifyForm(@RequestParam("retailerCode") int retailerCode, Model model) {
        log.debug("modifyForm() - 리테일러 수정 폼 호출 (retailerCode={})", retailerCode);
        RetailerDTO dto = retailerService.getOne(retailerCode);
        model.addAttribute("retailer", dto);
        return "retailer/mod";
    }

    // 7. 리테일러 수정 처리
    @PostMapping("/update.do")
    public String update(RetailerDTO dto, RedirectAttributes rttr) {
        log.debug("update() - 리테일러 수정 처리 (dto={})", dto);
        int flag = retailerService.doUpdate(dto);
        rttr.addFlashAttribute("msg", flag == 1 ? "리테일러 정보가 수정되었습니다." : "리테일러 정보 수정에 실패했습니다.");
        return "redirect:/retailer/detail.do?retailerCode=" + dto.getRetailerCode();
    }

    // 8. 리테일러 삭제 처리
    @PostMapping("/delete.do")
    public String delete(RetailerDTO dto, RedirectAttributes rttr) {
        log.debug("delete() - 리테일러 삭제 처리 (dto={})", dto);
        int flag = retailerService.doDelete(dto);
        rttr.addFlashAttribute("msg", flag == 1 ? "리테일러가 삭제되었습니다." : "리테일러 삭제에 실패했습니다.");
        return "redirect:/retailer/list.do";
    }

}
