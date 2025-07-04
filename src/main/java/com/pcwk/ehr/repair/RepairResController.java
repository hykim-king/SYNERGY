package com.pcwk.ehr.repair;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private RepairResService repairResService;

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

    /**
     * 리페어 신청 폼
     */
    @GetMapping("/form.do")
    public String showForm(HttpSession session, Model model) {
        if (session.getAttribute("login") == null) {
            MemberDTO dummyLogin = new MemberDTO();
            dummyLogin.setId("testUser");
            dummyLogin.setName("테스트유저");
            session.setAttribute("login", dummyLogin);
        }

        MemberDTO login = (MemberDTO) session.getAttribute("login");
        if (login == null) {
            return "redirect:/member/loginView.do";
        }

        model.addAttribute("loginId", login.getId());
        return "repair/repairForm"; // → /WEB-INF/views/repair/repairForm.jsp
    }

    /**
     * 리페어 신청 처리 → 결과 뷰
     */
    @PostMapping("/apply.do")
    public String applyRepair(RepairResDTO dto, Model model) throws Exception {
        int flag = repairResMapper.doSave(dto);
        model.addAttribute("dto", dto);
        model.addAttribute("success", flag == 1);
        return "repair/repairResult";
    }

    /**
     * 리페어 신청 저장 (AJAX 전송 대응)
     */
    @PostMapping("/reserve")
    @ResponseBody
    public Map<String, Object> reserve(RepairResDTO dto) throws Exception {
        int flag = repairResService.doSave(dto);
        Map<String, Object> result = new HashMap<>();
        result.put("status", flag > 0 ? "success" : "fail");
        return result;
    }

    /**
     * 신청 목록
     */
    @GetMapping("/list.do")
    public String listRepairs(Model model) {
        DTO search = new DTO();
        List<RepairResDTO> list = repairResMapper.doRetrieve(search);
        model.addAttribute("repairList", list);
        return "repair/repairList";
    }

    /**
     * 신청 상세 조회
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

    @GetMapping("/delete.do")
    public String deleteRepair(@RequestParam("repairNo") int repairNo, Model model) {
        RepairResDTO dto = new RepairResDTO();
        dto.setRepairNo(repairNo);
        int flag = repairResMapper.doDelete(dto);
        model.addAttribute("success", flag == 1);
        return "repair/repairResult";
    }
}