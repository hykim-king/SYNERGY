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

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.car.service.CarService;
import com.pcwk.ehr.cmn.PLog;

@Controller
@RequestMapping("admin/car")
public class CarAdminController implements PLog{
	
	@Autowired
	private CarService carService;
	
	@GetMapping("/list.do")
	public String adminList(
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
		    @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
		    @RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
		    @RequestParam(value = "searchType", required = false, defaultValue = "productName") String searchType,
		    Model model
	    ) {
		
		
        log.debug("┌─────────────────────────────┐");
        log.debug("│ adminList() - 관리자 차량 목록 조회 │");
        log.debug("└─────────────────────────────┘");
        
        if(pageNum < 1) pageNum = 1;
        
        int totalCount = carService.getCarCount();
        int totalPages = (int)Math.ceil((double)totalCount/pageNum);
        
        if(pageNum > totalPages) pageNum = totalPages > 0 ? totalPages : 1;
        
        List<CarDTO> carList = carService.getCarsByPage(pageNum, pageSize);
        
        
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
		if(flag>0) {
			log.debug("차량 등록 성공: " + car);
		}else {
			log.debug("차량 등록 실패");
		}		
		return "redirect:/admin/main.do";
	}
	
	

}
