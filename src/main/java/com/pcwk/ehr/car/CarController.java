package com.pcwk.ehr.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.car.service.CarService;
import com.pcwk.ehr.cmn.PLog;

@Controller
@RequestMapping("/car")
public class CarController implements PLog {

	@Autowired
	private CarService carService;

	// 전체 자동차 리스트
	@GetMapping("/list.do")
	public String list(Model model) {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ list() - 전체 차량 목록 조회                            │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		List<CarDTO> carList = carService.getAllCars();
		model.addAttribute("carList", carList);
		return "car/list";
	}

	// 브랜드별 자동차 리스트
	@GetMapping("/brand.do")
	public String listByBrand(@RequestParam("brand") String brand, Model model) {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ listByBrand() - 브랜드별 차량 조회 (brand={})          │", brand);
		log.debug("└─────────────────────────────────────────────────────────┘");
		List<CarDTO> carList = carService.getCarsByBrand(brand);
		model.addAttribute("carList", carList); // 조회된 차량 목록 사용
		model.addAttribute("selectedBrand", brand); // 선택된 브랜드 이름도 화면에 표시
		return "car/list";
	}

	// 자동차 정보 상세보기
	@GetMapping("/detail/{carCode}.do")
	public String detail(@PathVariable("carCode") int carCode, Model model) {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ detail() - 차량 상세 조회 (carCode={})                 │", carCode);
		log.debug("└─────────────────────────────────────────────────────────┘");
		CarDTO car = carService.getCarById(carCode);
		model.addAttribute("car", car);
		return "car/detail";

	}
}
