package atoz.cmm.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class Errors {
	
	public static String loginError(Model model) {		
		String err = (String) model.getAttribute("error");
		
		if ("validEmpty".equals(err)) {
			model.addAttribute("error", new ErrorVO("loginPage.do","로그인 오류 발생.","정보를 정확히 입력해 주세요."));
		} else if ("validEngNum".equals(err)) {
			model.addAttribute("error", new ErrorVO("loginPage.do","로그인 오류 발생.","영문/숫자만 입력해 주세요."));
		} else {
			model.addAttribute("error", new ErrorVO("loginPage.do","로그인 오류 발생","ID/PW를 적확히 입력해 주세요."));
		}		
		
		return "error";
	}
	
	public static String boardInsertError(Model model) {
		String err = (String) model.getAttribute("error");
		
		if ("validEmpty".equals(err)) {
			model.addAttribute("error", new ErrorVO("write.do","입력 오류발생.","제목/내용을 입력해 주세요."));
		} else if ("dbError".equals(err)) {
			model.addAttribute("error", new ErrorVO("write.do","입력 오류발생.","DB에 입력중 오류 발생."));
		} else{
			model.addAttribute("error", new ErrorVO("write.do","입력 오류발생","알수 없는 이유로 등록하지 못함."));
		}
		
		return "error";
	}

}
