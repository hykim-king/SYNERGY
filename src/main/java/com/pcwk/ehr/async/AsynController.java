/**
 * Package Name : com.pcwk.ehr.async <br/>
 * Class Name: AsynController.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-06-25<br/>
 *
 * ------------------------------------------<br/>
 * @author :user
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.async;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author user
 *
 */
@Controller
@RequestMapping("/async")
public class AsynController {
	Logger log = LogManager.getLogger(AsynController.class);
	
	public AsynController() {
//		log.debug("┌─────────────────────────────────────────────────────────┐");
//		log.debug("│ AsynController()                                        │");
//		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	
	@GetMapping("/async_index.do")
	public String syncIndex(HttpServletRequest req, Model model) throws InterruptedException{
		String viewName = "async/async_index";
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ doSaveView()                                            │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		//Thread.sleep(1*1000);
		String name = req.getParameter("name");
		
		log.debug("name:{}",name);
		
		//브라우저로 데이터 전송
		model.addAttribute("req_name",name);
		
		return viewName;
	}
	
	@RequestMapping(value="/async_result.do", method = RequestMethod.POST ,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String asyncResult(HttpServletRequest req) {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ asyncResult()                                           │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		
		log.debug("userId:{}",userId);
		log.debug("password:{}",password);
		
		return "PCWK_"+userId;
		
	}

}
