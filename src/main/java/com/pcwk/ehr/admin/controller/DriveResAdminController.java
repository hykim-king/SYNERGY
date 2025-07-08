package com.pcwk.ehr.admin.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.common.reflection.qual.GetClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.drive.DriveResDTO;
import com.pcwk.ehr.drive.DriveResService;

@Controller
@RequestMapping("/admin/drive")
public class DriveResAdminController implements PLog {

	@Autowired
	private DriveResService service;

	@GetMapping("/list.do")
	public String list(SearchDTO search, Model model) {

		log.debug("관리자 시승 예약 목록 조회");

		int pageNo = PcwkString.nvlZero(search.getPageNo(), 1);
		int pageSize = PcwkString.nvlZero(search.getPageSize(), 10);
		search.setPageNo(pageNo);
		search.setPageSize(pageSize);

		List<DriveResDTO> list = service.doRetrieve(search);
		model.addAttribute("list", list);
		model.addAttribute("param", search);

		return "admin/drive/list";
	}

}
