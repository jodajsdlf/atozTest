package atoz.member.controller;

import atoz.cmm.error.Errors;
import atoz.member.model.LoginModel;
import atoz.member.model.MemberModel;
import atoz.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

  @Autowired
  private MemberService memberService;

  @RequestMapping("loginPage.do")
  public String loginPage() {
    return "member/login";
  }

  @RequestMapping("login.do")
  public String login(LoginModel loginModel,HttpServletRequest request, Model model ) throws Exception{
    HttpSession session = request.getSession();
    try {
      memberService.login(session, request, loginModel);
      System.out.println("로그인 정보");
      System.out.println(memberService.login(session,request, loginModel));
      System.out.println("로그인 정보");
    }catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return Errors.loginError(model);
    }
    Thread.sleep(1000);
    return "redirect:/system/code/codePage.do";
  }

  @RequestMapping("/logout.do")
  public String logout(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession();
    session.invalidate();

    return "redirect:/system/code/codePage.do";
  }
}
