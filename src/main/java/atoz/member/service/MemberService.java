package atoz.member.service;

import atoz.member.model.LoginModel;
import atoz.member.model.MemberModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface MemberService {
  MemberModel login(HttpSession session, HttpServletRequest request,LoginModel loginModel) throws Exception;
}
