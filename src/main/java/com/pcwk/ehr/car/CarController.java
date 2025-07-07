package com.pcwk.ehr.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcwk.ehr.car.service.CarService;
import com.pcwk.ehr.cmn.PLog;

@Controller
@RequestMapping("/car")
public class CarController implements PLog {

    @Autowired
    private CarService carService;

    @GetMapping("/list.do")
    public String list(
        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
        Model model
    ) {
        log.debug("┌─────────────────────────────┐");
        log.debug("│ list() - 전체 차량 목록 조회(페이징) │");
        log.debug("└─────────────────────────────┘");

        // 전체 차량 수 조회
        int totalCount = carService.getCarCount();

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 해당 페이지 차량 목록 조회
        List<CarDTO> carList = carService.getCarsByPage(pageNum, pageSize);

        // View에 데이터 전달
        model.addAttribute("carList", carList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);

        return "car/list";
    }

    // 브랜드별 자동차 리스트
    @GetMapping("/brand.do")
    public String listByBrand(@RequestParam("brand") String brand, Model model) {
        log.debug("┌─────────────────────────────────────────────────┐");
        log.debug("│ listByBrand() - 브랜드별 차량 조회 (brand={})          │", brand);
        log.debug("└─────────────────────────────────────────────────┘");

        List<CarDTO> carList = carService.getCarsByBrand(brand);
        model.addAttribute("carList", carList);
        model.addAttribute("selectedBrand", brand);
        return "car/list";
    }

    // 자동차 정보 상세보기
    @GetMapping("/detail.do")
    public String detail(@RequestParam("carCode") int carCode, Model model) {
        log.debug("┌─────────────────────────────┐");
        log.debug("│ detail() - 차량 상세 조회 (carCode={}) │", carCode);
        log.debug("└─────────────────────────────┘");

        CarDTO car = carService.getOne(carCode);
        model.addAttribute("car", car);
        return "car/detail";
    }

    // 자동차 정보 수정 처리
    @PostMapping("/update.do")
    public String update(CarDTO car, RedirectAttributes rttr) {
        log.debug("┌─────────────────────────────┐");
        log.debug("│ update() - 차량 정보 수정 처리 (car={}) │", car);
        log.debug("└─────────────────────────────┘");

        int flag = carService.update(car);
        if (flag == 1) {
            rttr.addFlashAttribute("msg", "차량 정보가 수정되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "차량 정보 수정에 실패했습니다.");
        }
        // 수정 후 상세 페이지로 리다이렉트
        return "redirect:/car/detail.do?carCode=" + car.getCarCode();
    }

    // 자동차 정보 삭제 처리
    @PostMapping("/delete.do")
    public String delete(@RequestParam("carCode") int carCode, RedirectAttributes rttr) {
        log.debug("┌─────────────────────────────┐");
        log.debug("│ delete() - 차량 정보 삭제 처리 (carCode={}) │", carCode);
        log.debug("└─────────────────────────────┘");

        int flag = carService.deleteById(carCode);
        if (flag == 1) {
            rttr.addFlashAttribute("msg", "차량 정보가 삭제되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "차량 정보 삭제에 실패했습니다.");
        }
        // 삭제 후 목록 페이지로 리다이렉트
        return "redirect:/car/list.do";
    }

}
