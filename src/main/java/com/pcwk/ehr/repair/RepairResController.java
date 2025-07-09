package com.pcwk.ehr.repair;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.RepairResMapper;
import com.pcwk.ehr.member.MemberDTO;
import com.pcwk.ehr.retailer.RetailerDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/repair")
public class RepairResController {

    Logger log = LogManager.getLogger(getClass());

    @Autowired
    private RepairResMapper repairResMapper;

 

    /**
     * 정비신청 폼
     */
    @GetMapping("/form.do")
    public String showForm(HttpSession session, Model model) {
    //테스트 아이디 주입
    	/*    if (session.getAttribute("login") == null) {
            MemberDTO dummyLogin = new MemberDTO();
            dummyLogin.setId("testUser");
            dummyLogin.setName("테스트유저");
            session.setAttribute("login", dummyLogin);
        }*/

        MemberDTO login = (MemberDTO) session.getAttribute("loginUser");
        if (login == null) {
            return "redirect:/member/loginView.do";
        }

        model.addAttribute("loginId", login.getId());
        return "repair/repairForm"; // → /WEB-INF/views/repair/repairForm.jsp
    }

    /**
     * 리페어 신청 처리 → 결과 페이지로 이동
     */
    @PostMapping("/apply.do")
    public String applyRepair(RepairResDTO dto, Model model) throws Exception {
        int flag = repairResMapper.doSave(dto); // 신청내용 저장
        RepairResDTO outDto = repairResMapper.doSelectOne(dto); // 저장된 정보 다시 가져옴
        
        //히든값 가져오려고 필요한 정보 조회 
        CarDTO car = 
        	repairResMapper.getCarInfoByCode(outDto.getCarCode()); // 차량 정보조회
        
        RetailerDTO retailer = 
        	repairResMapper.getRetailerInfoByCode(outDto.getRetailerCode()); //업체 정보조회
        
        // 추가 정보 outDto에 불러와
        outDto.setCarMf(car.getCarMf());
        outDto.setProductName(car.getProductName());
        outDto.setRetailerName(retailer.getRetailerName());
        
        // 모델에 outDto 값 추가
            model.addAttribute("dto", outDto);
        model.addAttribute("success", flag == 1);
        return "repair/repairResult";
        
    }

    /**
     * 신청 목록: 로그인 정보를 반영해야함.
     */
    @GetMapping("/list.do")
    public String list(HttpSession session, Model model) {
        MemberDTO loginUser = (MemberDTO)session.getAttribute("loginUser");
        if(loginUser == null) {
        	return "redirect:/member/loginView.do"; 			
        }
        
        String loginId = loginUser.getId();
        
        RepairResDTO inDto = new RepairResDTO();
        inDto.setId(loginId); // 로그인된 아이디값 가져오기
        
        List<RepairResDTO> repairList = repairResMapper.doRetrieveByUser(inDto);
        model.addAttribute("repairList", repairList);
        return "repair/repairList";
    }

    
    
    // 정비신청 취소 -신청목록에서 삭제
    @GetMapping("/delete.do")
    public String deleteRepair(@RequestParam("repairNo") int repairNo, RedirectAttributes redirectAttributes) {
        RepairResDTO dto = new RepairResDTO();
        dto.setRepairNo(repairNo);
        int flag = repairResMapper.doDelete(dto);
        if (flag == 1) {
            redirectAttributes.addFlashAttribute("msg", "예약이 성공적으로 취소되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("msg", "예약 취소에 실패했습니다.");
        }
        return "redirect:/repair/list.do";
    }
    
    

    /**
     * 이하 기능 사용 x
     */
    @GetMapping("/detail.do")
    public String detail(@RequestParam("repairNo") int repairNo, Model model) {
        RepairResDTO dto = new RepairResDTO();
        dto.setRepairNo(repairNo);
        RepairResDTO out = repairResMapper.doSelectOne(dto);
        model.addAttribute("repair", out);
        return "repair/repairDetail";
    }
    
    @PostMapping("/update.do")
    public String updateRepair(RepairResDTO dto, Model model) {
        int flag = repairResMapper.doUpdate(dto);
        model.addAttribute("dto", dto);
        model.addAttribute("success", flag == 1);
        return "repair/repairResult";
    }

    
  //=========================  JSON 응답 메서드 ============================//
    // 1) 제조사(JSON)
    @GetMapping("/mfList.do")
    @ResponseBody
    public List<String> mfList() {
        return repairResMapper.mfList();
    }

    // 2) 제품명+코드(JSON)
    @GetMapping("/productList.do")
    @ResponseBody
    public List<CarDTO> productList(@RequestParam String carMf) {
        return repairResMapper.productList(carMf);
    }

    // 3) 업체(JSON)
    @GetMapping("/retailerList.do")
    @ResponseBody
    public List<RetailerDTO> retailerList(@RequestParam String productName) {
        return repairResMapper.retailerList(productName);
    }

    // 4) 차량 코드 조회
    @GetMapping("/getCode.do")
    @ResponseBody
    public int getCode(@RequestParam String carMf,
                       @RequestParam String productName) {
        return repairResMapper.getCarCode(carMf, productName);
    }
}