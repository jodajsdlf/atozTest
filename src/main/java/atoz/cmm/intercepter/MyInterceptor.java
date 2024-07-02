package atoz.cmm.intercepter;

import atoz.member.model.MemberModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {
  @Override // 요청 전 처리 로직
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod hd = (HandlerMethod) handler;
      System.out.println(hd.getBeanType().getSimpleName() + " : " + hd.getMethod().getName() + " 실행 전 실행되는 인터셉터--------------");

      HttpSession session = request.getSession();
      MemberModel loginInfo = (MemberModel) session.getAttribute("loginInfo");

      // 리다이렉트 경로를 절대 경로로 설정
      if (loginInfo == null) {
        response.sendRedirect(request.getContextPath() + "/loginPage.do");
        return false;
      }
    }
    return true; // true를 반환하면 요청이 계속 진행됩니다.
  }

  @Override // 요청 후 처리 로직
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod hd = (HandlerMethod) handler;
      System.out.println(hd.getBeanType().getSimpleName() + " : " + hd.getMethod().getName() + " 실행 후 실행되는 인터셉터==============");
    }
  }

  @Override // 뷰가 렌더링된 후 처리 로직
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod hd = (HandlerMethod) handler;
      System.out.println(hd.getBeanType().getSimpleName() + " : " + hd.getMethod().getName() + " 이후 뷰 렌더링 후 실행되는 인터셉터+++++++++++++++++++");
    }
  }
}
