package com.pcwk.ehr.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcwk.ehr.car.service.CarService;
import com.pcwk.ehr.cmn.PLog;

@Controller
@RequestMapping("/car")
public class CarController implements PLog {

    @Autowired
    private CarService carService;

    // (1) 전체 자동차 리스트 
    // ( 차량 목록 페이지 -> 검색 / 페이징 기능을 지원하게 하는 컨트롤러 메서드 )
    @GetMapping("/list.do")
    public String list(
        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
        @RequestParam(value = "searchType", required = false, defaultValue = "productName") String searchType,
        @RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
        Model model
    ) {
        // 1. 전체 데이터 개수 조회 (검색 조건 반영)
        int totalCount = carService.getCarCountWithSearch(searchType, searchWord);

        // 2. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 3. pageNum 범위 보정
        if(totalPages > 0 && pageNum > totalPages) {
            pageNum = totalPages;
        }
        if(pageNum < 1) {
            pageNum = 1;
        }

        // 4. 보정된 pageNum으로 데이터 조회
        List<CarDTO> carList = carService.getCarsByPageWithSearch(pageNum, pageSize, searchType, searchWord);

        // 5. 모델에 데이터와 페이징 정보 전달
        model.addAttribute("carList", carList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("totalPages", totalPages);

        return "car/list";
    }
    

    // (2) 브랜드별 자동차 리스트
    @GetMapping("/carMf.do")
    public String listByBrand(
        @RequestParam("carMf") String carMf, 
        Model model) 
    {
        log.debug("┌─────────────────────────────────────────────────┐");
        log.debug("│ listByBrand() - 브랜드별 차량 조회 (brand={})          │", carMf);
        log.debug("└─────────────────────────────────────────────────┘");

        List<CarDTO> carList = carService.getCarsByBrand(carMf);
        model.addAttribute("carList", carList);
        model.addAttribute("selectedBrand", carMf);
        return "car/list";
    }

    // (3) 자동차 상세 조회
    @GetMapping("/detail.do")
    public String detail(@RequestParam("carCode") int carCode, Model model) {
        log.debug("┌─────────────────────────────┐");
        log.debug("│ detail() - 차량 상세 조회 (carCode={}) │", carCode);
        log.debug("└─────────────────────────────┘");

        CarDTO car = carService.getOne(carCode);
        model.addAttribute("car", car);
        return "car/detail";
    }

    // (4) 자동차 정보 수정 처리
    @PostMapping("/update.do")
    public String update(CarDTO car, RedirectAttributes rttr) {
        log.debug("┌─────────────────────────────┐");
        log.debug("│ update() - 차량 정보 수정 처리 (car={}) │", car);
        log.debug("└─────────────────────────────┘");

        int flag = carService.update(car);
        if (flag == 1) {
            rttr.addFlashAttribute("msg", "차량 정보가 수정되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "차량 정보 수정에 실패했습니다.");
        }
        return "redirect:/car/detail.do?carCode=" + car.getCarCode();
    }

    // (5) 자동차 정보 삭제 처리
    @PostMapping("/delete.do")
    public String delete(@RequestParam("carCode") int carCode, RedirectAttributes rttr) {
        log.debug("┌─────────────────────────────┐");
        log.debug("│ delete() - 차량 정보 삭제 처리 (carCode={}) │", carCode);
        log.debug("└─────────────────────────────┘");

        int flag = carService.deleteById(carCode);
        if (flag == 1) {
            rttr.addFlashAttribute("msg", "차량 정보가 삭제되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "차량 정보 삭제에 실패했습니다.");
        }
        return "redirect:/car/list.do";
    }

}
