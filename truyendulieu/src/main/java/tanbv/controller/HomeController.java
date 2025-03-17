package tanbv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController{
	@PostMapping("/infor")
	public String inforPost(@RequestParam(name="id",defaultValue = "000000") String id,@RequestParam(name="password",defaultValue = "00000") String password,ModelMap model) {
		if(id.equals("64132105")&&password.equals("tan123")) {
			model.addAttribute("ten","Bùi Vũ Tân");
			model.addAttribute("mssv","64132105");
			return("inforPage");
		}
		return "loginPage";
	}
	@GetMapping("/infor")
	public String inforGet(@RequestParam(name="id",defaultValue = "000000") String id,@RequestParam(name="password",defaultValue = "00000") String password,ModelMap model) {
		if(id.equals("64132105")&&password.equals("tan123")) {
			model.addAttribute("ten","Bùi Vũ Tân");
			model.addAttribute("mssv","64132105");
			return("inforPage");
		}
		return "loginPage";
	}
	@GetMapping("/login")
	public String login() {
		return "loginPage";
	}
	
	@GetMapping("/BMICal")
	public String BMICalculation() {
		return "BMICal";
	}
	
	@PostMapping("/BMICalPost")
	public String BMIPost(@RequestParam("height") float height,@RequestParam("weight") float weight,ModelMap model) {
		float bmi=(float) (weight/(Math.pow(height/100,2)));
		String thongbao="";
		if (bmi < 18.5) {
            thongbao += "Bạn bị thiếu cân.";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            thongbao += "Bạn có cân nặng bình thường.";
        } else if (bmi >= 25 && bmi < 29.9) {
            thongbao += "Bạn thừa cân.";
        } else {
            thongbao += "Bạn béo phì.";
        }
		model.addAttribute("thongbao",thongbao);
		model.addAttribute("kq",bmi);
		return "BMICal";
	}

	@GetMapping("/BMICalGet")
	public String BMIGet(@RequestParam("height") float height,@RequestParam("weight") float weight,ModelMap model) {
		float bmi=(float) (weight/(Math.pow(height/100,2)));
		String thongbao="";
		if (bmi < 18.5) {
            thongbao += "Bạn bị thiếu cân.";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            thongbao += "Bạn có cân nặng bình thường.";
        } else if (bmi >= 25 && bmi < 29.9) {
            thongbao += "Bạn thừa cân.";
        } else {
            thongbao += "Bạn béo phì.";
        }
		model.addAttribute("thongbao",thongbao);
		model.addAttribute("kq",bmi);
		return "BMICal";
	}
}