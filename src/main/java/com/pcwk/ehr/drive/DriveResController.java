package com.pcwk.ehr.drive;


import org.apache.logging.log4j.Logger;
import org.checkerframework.common.reflection.qual.GetClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.DriveResMapper;
import com.pcwk.ehr.member.MemberDTO;
import com.pcwk.ehr.retailer.RetailerDTO;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.DriveResMapper;
import com.pcwk.ehr.member.MemberDTO;

@Controller
@RequestMapping("/drive")
public class DriveResController {

	Logger log=LogManager.getLogger(GetClass.class);
	
    @Autowired
    private DriveResMapper driveResMapper;
    
    // 1) 제조사(JSON)
    @GetMapping("/mfList.do")
    @ResponseBody
    public List<String> mfList() {
        return driveResMapper.mfList();
    
    }

    // 2) 제품명+코드(JSON)
    @GetMapping("/productList.do")
    @ResponseBody
    public List<CarDTO> productList(@RequestParam String carMf) {
        return driveResMapper.productList(carMf);
    }

    // 3) 업체(JSON)
    @GetMapping("/retailerList.do")
    @ResponseBody
    public List<RetailerDTO> retailerList(@RequestParam String productName) {
        return driveResMapper.retailerList(productName);
    }
    
    @GetMapping("/getCode.do")
    @ResponseBody
    public int getCode(@RequestParam String carMf,
                       @RequestParam String productName) {
        return driveResMapper.getCarCode(carMf, productName);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 시승 신청 화면 보여주기 (테스트용 세션 포함)
     */
    @GetMapping("/form.do")
    public String showForm(HttpSession session, Model model) {
       //   테스트용 임시 로그인 세션 삽입
        if (session.getAttribute("login") == null) {
            MemberDTO dummyLogin = new MemberDTO();
            dummyLogin.setId("testUser");
            dummyLogin.setName("테스트유저");
            session.setAttribute("login", dummyLogin);
        }

   
        
        //  로그인 검사
        MemberDTO login = (MemberDTO) session.getAttribute("login");
        if (login == null) {
            return "redirect:/member/loginView.do";
        }

        // 로그인한 사용자 정보 뷰로 전달
        model.addAttribute("loginId", login.getId());
        return "drive/driveForm"; // → /WEB-INF/views/drive/driveForm.jsp
    }

    // 추후 실서비스 시
    // (위의 세션 삽입 코드 제거)
    /*
    @GetMapping("/form.do")
    public String showForm(HttpSession session, Model model) {
        MemberDTO login = (MemberDTO) session.getAttribute("login");
        if (login == null) {
            return "redirect:/member/login.do";
        }
        model.addAttribute("loginId", login.getId());
        return "drive/driveForm";
    }
    */



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

    // 시승신청정보를 DB에 저장
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