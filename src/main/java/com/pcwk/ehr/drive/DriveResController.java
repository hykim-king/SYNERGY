package com.pcwk.ehr.drive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.DriveResMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/drive")
public class DriveResController {

    @Autowired
    private DriveResMapper driveResMapper;

    /**
     * 시승 신청 화면 보여주기
     */
    @GetMapping("/form.do")
    public String showForm() {
        return "drive/driveForm"; // JSP 또는 HTML 페이지 이름
    }

    /** 시승 신청 처리 → 결과 페이지로 이동 **/
    @PostMapping("/apply.do")
    public String applyDrive(DriveResDTO dto, Model model) throws Exception {
        int flag = driveResMapper.doSave(dto);
        // 뷰에서 꺼내 쓸 속성들
        model.addAttribute("dto", dto);
        model.addAttribute("success", flag == 1);
        return "drive/driveResult";
    }
    
    
    @Autowired
    private DriveResService driveResService;

    // ← 기존에 apply.do를 처리하시던 메서드가 있을 겁니다.
    //    그 메서드 바로 아래에 붙여 넣으시면 돼요.

    @PostMapping("/reserve")  // 또는 .do를 유지하고 싶으면 "/apply.do"
    @ResponseBody
    public Map<String, Object> reserve(DriveResDTO dto) throws Exception {
        int flag = driveResService.doSave(dto);
        Map<String, Object> result = new HashMap<>();
        result.put("status", flag > 0 ? "success" : "fail");
        return result;
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