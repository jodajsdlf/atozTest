/*
package atoz.page.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.atoz.main.member.service.LoginInfoDTO; 

@Controller
public class HeaderController {
	
	@Value("${upload.dir}")
	private String UPLOAD_DIR;
	
	@RequestMapping("/systemAdmin/getHeader.do")
	public String sysGetHeader(@RequestParam String pageName, Model model, HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		LoginInfoDTO loginInfo = (LoginInfoDTO) session.getAttribute("loginInfo");
		

		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("pageName", pageName);
		
		return "/header/system_admin_header";
	}
	
	@RequestMapping("/companyAdmin/getHeader.do")
	public String comGetHeader(@RequestParam String pageName, Model model, HttpSession session) throws Exception{
		LoginInfoDTO loginInfo = (LoginInfoDTO) session.getAttribute("loginInfo");

		model.addAttribute("loginInfo", loginInfo);  
		model.addAttribute("pageName", pageName);
		return "/header/company_admin_header";
	}
	
	@RequestMapping("/departmentManager/getHeader.do")
	public String depGetHeader(@RequestParam String pageName, Model model, HttpSession session) throws Exception{
		LoginInfoDTO loginInfo = (LoginInfoDTO) session.getAttribute("loginInfo");
		
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("pageName", pageName);
		return "/header/department_manager_header";
	}
	
}
*/
