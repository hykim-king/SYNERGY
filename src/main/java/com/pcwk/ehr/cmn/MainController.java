package com.pcwk.ehr.cmn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
	
	public MainController() {
		
	}
	
	@GetMapping("/main.do")
	public String mainView() {
		return "main/main";
	}

}
