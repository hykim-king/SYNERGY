package com.pcwk.ehr.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    		// @RequestParam -> 파라미터 이름으로 바인딩한다
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
        if(totalPages > 0 && pageNum > totalPages) { 	//1페이지부터 시작
            pageNum = totalPages;
        }
        if(pageNum < 1) { 	
            pageNum = 1;
        }

        // 4. 보정된 pageNum으로 데이터 조회
        List<CarDTO> carList = carService.getCarsByPageWithSearch(pageNum, pageSize, searchType, searchWord);

        // 5. 모델에 데이터와 페이징 정보 전달
        model.addAttribute("carList", carList);
        model.addAttribute("currentPage", pageNum);   	//현재 페이지
        model.addAttribute("pageSize", pageSize);  		//페이지 사이즈
        model.addAttribute("searchType", searchType);	//검색 유형
        model.addAttribute("searchWord", searchWord);	//검색 단어
        model.addAttribute("totalPages", totalPages);	//총 페이지

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

}
