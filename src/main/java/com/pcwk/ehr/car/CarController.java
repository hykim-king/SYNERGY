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

	@GetMapping("/list.do")
	public String list(
	    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	    @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
	    Model model
	) {
	    log.debug("┌─────────────────────────────┐");
	    log.debug("│ list() - 전체 차량 목록 조회(페이징) │");
	    log.debug("└─────────────────────────────┘");

	    // 전체 차량 수 조회
	    int totalCount = carService.getCarCount();

	    // 전체 페이지 수 계산
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);

	    // 해당 페이지 차량 목록 조회
	    List<CarDTO> carList = carService.getCarsByPage(pageNum, pageSize);

	    // View에 데이터 전달
	    model.addAttribute("carList", carList);
	    model.addAttribute("currentPage", pageNum);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("totalCount", totalCount);

	    return "car/list";
	}


	// 브랜드별 자동차 리스트
	@GetMapping("/brand.do")
	public String listByBrand(@RequestParam("brand") String brand, Model model) {
		log.debug("┌─────────────────────────────────────────────────┐");
		log.debug("│ listByBrand() - 브랜드별 차량 조회 (brand={})          │", brand);
		log.debug("└─────────────────────────────────────────────────┘");
		List<CarDTO> carList = carService.getCarsByBrand(brand);
		model.addAttribute("carList", carList); // 조회된 차량 목록 사용
		model.addAttribute("selectedBrand", brand); // 선택된 브랜드 이름도 화면에 표시
		return "car/list";
	}

	// 자동차 정보 상세보기
	 @GetMapping("/detail")
	    public String detail(@RequestParam("carCode") int carCode, Model model) {
	        CarDTO car = carService.getOne(carCode);
	        model.addAttribute("car", car);
	        return "car/detail";
	    }

}
