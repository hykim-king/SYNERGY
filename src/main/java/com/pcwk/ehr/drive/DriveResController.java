package com.pcwk.ehr.drive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.DriveResMapper;

import java.util.List;

@Controller
@RequestMapping("drive")
public class DriveResController {

    @Autowired
    DriveResMapper driveResMapper;

    /**
     * 시승 신청 화면 보여주기
     */
    @GetMapping("/form.do")
    public String showForm() {
        return "drive/driveForm"; // JSP 또는 HTML 페이지 이름
    }

    /**
     * 시승 신청 처리
     */
    @PostMapping("/apply.do")
    public String applyDrive(@ModelAttribute DriveResDTO dto, Model model) {
        int flag = driveResMapper.doSave(dto);
        model.addAttribute("message", (flag == 1) ? "시승 신청 성공!" : "시승 신청 실패..");
        return "drive/result"; // 결과 화면
    }

    /**
     * 시승 신청 목록 보기 (예: 마이페이지)
     */
    @GetMapping("/list.do")
    public String listDrives(Model model) {
    	DTO search= new DTO();
        List<DriveResDTO> list = driveResMapper.doRetrieve(search);
        model.addAttribute("driveList", list);
        return "drive/driveList"; // 목록 출력 페이지
    }

    /**
     * 시승 신청 상세 조회
     */
    @GetMapping("/detail.do")
    public String detail(@RequestParam("resNo") int resNo, Model model) {
        DriveResDTO dto = new DriveResDTO();
        dto.setResNo(resNo);
        DriveResDTO out = driveResMapper.doSelectOne(dto);
        model.addAttribute("drive", out);
        return "drive/driveDetail"; // 상세 페이지
    }

}