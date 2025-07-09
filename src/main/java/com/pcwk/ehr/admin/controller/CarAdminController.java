package com.pcwk.ehr.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.car.service.CarService;
import com.pcwk.ehr.cmn.PLog;

@Controller
@RequestMapping("admin/car")
public class CarAdminController implements PLog {

	@Autowired
	private CarService carService;

	@GetMapping("/list.do")
	public String adminList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
	        @RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
	        @RequestParam(value = "searchType", required = false, defaultValue = "productName") String searchType,
	        Model model) {

	    log.debug("adminList() - 관리자 차량 목록 조회");

	    if (pageNum < 1)
	        pageNum = 1;

	    int totalCount = 0;
	    List<CarDTO> carList = null;

	    if (searchWord == null || searchWord.trim().isEmpty()) {
	        // 검색어 없으면 기존 방식으로 전체 목록
	        totalCount = carService.getCarCount();
	        carList = carService.getCarsByPage(pageNum, pageSize);
	    } else {
	        // 검색어 있을 때는 검색 조건 반영
	        totalCount = carService.getCarCountWithSearch(searchType, searchWord);
	        carList = carService.getCarsByPageWithSearch(pageNum, pageSize, searchType, searchWord);
	    }

	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);

	    if (pageNum > totalPages)
	        pageNum = totalPages > 0 ? totalPages : 1;

	    model.addAttribute("carList", carList);
	    model.addAttribute("currentPage", pageNum);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("totalCount", totalCount);
	    model.addAttribute("searchType", searchType);
	    model.addAttribute("searchWord", searchWord);

	    return "admin/car/list";
	}

	@GetMapping("/add.do")
	public String addCarView(Model model) {
		model.addAttribute("car", new CarDTO());
		return "admin/car/addCar";
	}

	@PostMapping("/add.do")
	public String addCar(@ModelAttribute CarDTO car) {
		int flag = carService.save(car);
		if (flag > 0) {
			log.debug("차량 등록 성공: " + car);
		} else {
			log.debug("차량 등록 실패");
		}
		return "redirect:/admin/main.do";
	}
	
	@GetMapping("/updateView.do")
	public String updateView(@RequestParam("carCode") int carCode, Model model) {
	    log.debug("┌─────────────────────────────┐");
	    log.debug("│ updateView() - 차량 정보 수정 페이지 조회 (carCode={}) │", carCode);
	    log.debug("└─────────────────────────────┘");

	    CarDTO car = carService.getCarById(carCode);
	    if (car == null) {
	        // 차량 정보가 없으면 목록으로 리다이렉트하면서 메시지 띄우도록 처리해도 됩니다.
	        model.addAttribute("msg", "해당 차량 정보를 찾을 수 없습니다.");
	        return "redirect:/admin/car/list.do";
	    }

	    model.addAttribute("car", car);
	    return "/admin/car/updateCar"; // 수정 페이지 JSP 경로 (파일명 맞게 변경하세요)
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
		return "redirect:/admin/car/list.do";
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
		return "redirect:/admin/car/list.do";
	}

}