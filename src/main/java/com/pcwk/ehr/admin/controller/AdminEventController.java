package com.pcwk.ehr.admin.controller;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.event.EventDTO;
import com.pcwk.ehr.event.EventService;
import com.pcwk.ehr.member.MemberDTO;

@Controller
@RequestMapping("/admin/event")
public class AdminEventController {

    @Autowired
    EventService eventService;

    // 이벤트 목록
    @GetMapping("/eve_list.do")
    public String eventList(SearchDTO param, Model model, HttpSession session) {
        param.setDiv("이벤트"); // 🔹 반드시 세팅
        
        if (param.getPageNo() == 0) param.setPageNo(1);
        if (param.getPageSize() == 0) param.setPageSize(10);

        List<EventDTO> list = eventService.doRetrieve(param);
        int totalCnt = list.isEmpty() ? 0 : list.get(0).getTotalCnt();

        model.addAttribute("list", list);
        model.addAttribute("search", param);
        model.addAttribute("totalCnt", totalCnt);

        return "admin/event/eve_list";
    }

    // 이벤트 등록 화면
    @GetMapping("/eve_reg.do")
    public String eventReg() {
        return "admin/event/eve_reg";
    }

    // 이벤트 등록 처리
    @PostMapping("/save.do")
    public String save(EventDTO dto, HttpSession session) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser != null) {
            dto.setRegId(loginUser.getId());
            dto.setEmail(loginUser.getEmail());
            dto.setNickname(loginUser.getNickname());
        } else {
            // 비로그인 시 기본값
            dto.setRegId("anonymous");
            dto.setEmail("anonymous@example.com");
            dto.setNickname("익명");
        }

        dto.setDiv("이벤트");

        int result = eventService.doSave(dto);

        // 등록 성공 시 관리자 목록 페이지로 리디렉션
        return "redirect:/admin/event/eve_list.do";
    }

    // 이벤트 수정 화면
    @GetMapping("/eve_mod.do")
    public String eventMod(int ecode, Model model) {
        EventDTO param = new EventDTO();
        param.setEcode(ecode);
        EventDTO out = eventService.doSelectOne(param);
        model.addAttribute("event", out);

        return "admin/event/eve_mod";
    }

    // 이벤트 수정 처리
    @PostMapping("/update.do")
    public String update(EventDTO dto, HttpSession session) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser != null) {
            dto.setModId(loginUser.getId());
        } else {
            dto.setModId("anonymous");
        }

        // 기본값 설정
        if (dto.getDiv() == null || dto.getDiv().isEmpty()) {
            dto.setDiv("이벤트");
        }

        int result = eventService.doUpdate(dto);

        // 수정 후 목록 페이지로 이동
        return "redirect:/admin/event/eve_list.do";
    }
    // 이벤트 삭제 처리
    @PostMapping("/delete.do")
    public String delete(@RequestParam(value = "ecodeList", required = false) List<Integer> ecodeList) {
        if (ecodeList != null) {
            for (int code : ecodeList) {
                EventDTO dto = new EventDTO();
                dto.setEcode(code);
                eventService.doDelete(dto);
            }
        }

        // 삭제 후 이벤트 목록 페이지로 리다이렉트
        return "redirect:/admin/event/eve_list.do";
    }

    @PostConstruct
    public void init() {
        System.out.println(">>> AdminEventController (권한 없이 등록 가능) 로드 완료 <<<");
    }
}