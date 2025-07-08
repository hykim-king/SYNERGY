package com.pcwk.ehr.drive;

import org.apache.logging.log4j.Logger;
import org.checkerframework.common.reflection.qual.GetClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    
    // 업체 상세보기
    @GetMapping("/retailer-detail.do")
    @ResponseBody
    public RetailerDTO getRetailerDetailFromDrive(@RequestParam("retailerCode") int retailerCode) {
        RetailerDTO dto = driveResMapper.getRetailerInfoByCode(retailerCode); 
        return dto;
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
//    @GetMapping("/form.do")
//    public String showForm(HttpSession session, Model model) {
//       //   테스트용 임시 로그인 세션 삽입
//        if (session.getAttribute("login") == null) {
//            MemberDTO dummyLogin = new MemberDTO();
//            dummyLogin.setId("testUserAgain");
//            dummyLogin.setName("테스트유저또해");
//            session.setAttribute("login", dummyLogin);
//        }

        // 추후 실서비스 시
        // (위의 세션 삽입 코드 제거)
   
        @GetMapping("/form.do")
        public String showForm(HttpSession session, Model model) {
        MemberDTO login = (MemberDTO) session.getAttribute("loginUser");
/*            if (login == null) {
                return "redirect:/member/login.do";
            }
            model.addAttribute("loginId", login.getId());
            return "drive/driveForm";
        }
        */
       
        
        //  테스트 로그인 검사
   //     MemberDTO login = (MemberDTO) session.getAttribute("login");
        if (login == null) {
            return "redirect:/member/loginView.do";
        }

        // 로그인한 사용자 정보 뷰로 전달
        model.addAttribute("loginId", login.getId());
        return "drive/driveForm"; // → /WEB-INF/views/drive/driveForm.jsp
    }



    /** 시승 신청 처리 → 결과 페이지로 이동 **/

    @PostMapping("/apply.do")
    public String applyDrive(DriveResDTO dto, Model model) throws Exception {
        // 1. 신청 저장
        int flag = driveResMapper.doSave(dto);

        // 2. 저장된 정보 다시 조회(resNo 포함)
        DriveResDTO outDto = driveResMapper.doSelectOne(dto);

        // 3. 차량 정보 조회
        CarDTO car = driveResMapper.getCarInfoByCode(outDto.getCarCode());

        // 4. 업체 정보 조회
        RetailerDTO retailer = driveResMapper.getRetailerInfoByCode(outDto.getRetailerCode());

        // 5. 필요한 정보 outDto에 추가
        outDto.setCarMf(car.getCarMf());
        outDto.setProductName(car.getProductName());
        outDto.setRetailerName(retailer.getRetailerName());

        // 6. 모델에 값 추가
        model.addAttribute("dto", outDto);
        model.addAttribute("success", flag == 1);

        return "drive/driveResult";
    }
    
    
    
    /**
     * 시승 신청 목록 보기 (로그인 정보 추가)
     */
    @GetMapping("/list.do")
    public String list(HttpSession session, Model model) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/member/loginView.do";
        }

        String loginId = loginUser.getId();

        DriveResDTO inDto = new DriveResDTO();
        inDto.setId(loginId); // 로그인 ID만 설정

        List<DriveResDTO> driveList = driveResMapper.doRetrieveByUser(inDto);
        model.addAttribute("driveList", driveList);

        return "drive/driveList";
    }

    //예약 취소(삭제)
    @GetMapping("/delete.do")
    public String deleteDrive(@RequestParam("resNo") int resNo, RedirectAttributes redirectAttributes) {
        DriveResDTO dto = new DriveResDTO();
        dto.setResNo(resNo);

        int flag = driveResMapper.doDelete(dto);

        if (flag == 1) {
            redirectAttributes.addFlashAttribute("msg", "예약이 성공적으로 취소되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("msg", "예약 취소에 실패했습니다.");
        }

        return "redirect:/drive/list.do";
    }
    
    
    /**
     * 이하 기능 실행 안함
     */
    
    @GetMapping("/detail.do")
    public String detail(@RequestParam("resNo") int resNo, Model model) {
        DriveResDTO dto = new DriveResDTO();
        dto.setResNo(resNo);
        DriveResDTO out = driveResMapper.doSelectOne(dto);
        model.addAttribute("drive", out);
        return "drive/driveDetail"; // 상세 페이지
    }
    
    @PostMapping("/update.do")
    public String updateDrive(DriveResDTO dto, Model model) {
        int flag = driveResMapper.doUpdate(dto);
        model.addAttribute("dto", dto);
        model.addAttribute("success", flag == 1);
        return "drive/driveResult";
    }


    

}