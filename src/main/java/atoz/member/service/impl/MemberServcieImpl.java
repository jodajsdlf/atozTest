package atoz.member.service.impl;

import atoz.cmm.ValidationForm;
import atoz.member.model.LoginModel;
import atoz.member.model.MemberModel;
import atoz.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServcieImpl implements MemberService {

  private static final Logger logger = LoggerFactory.getLogger(ValidationForm.class);

  @Autowired
  MemberMapper mapper;

  @Override
  public MemberModel login(HttpSession session, HttpServletRequest request, LoginModel loginModel) throws Exception {
    // 로그인 시작 로그 출력
    logger.info("################################### login ###################################");

    // 입력값 로그 출력
    logger.info("로그인 시도 user_id: " + loginModel.getMemberId());

    // 입력 값 검증
    if (!ValidationForm.validEngNum(loginModel.getMemberId()) || !ValidationForm.validEngNum(loginModel.getMemberPw())) {
      logger.error("user_id 또는 user_password 검증 실패");
      throw new Exception("validEngNum");
    }

    // 로그인 시도
    logger.info("로그인 시도 user_id: " + loginModel.getMemberId());
    MemberModel memberModel = mapper.login(loginModel);

    if (memberModel == null) {
      logger.error("로그인 실패 user_id: " + loginModel.getMemberId());
      throw new Exception("loginError");
    }

    /*// 클라이언트 IP 주소 가져오기
    String ipAddress = request.getHeader("X-Forwarded-For");
    if (ipAddress == null || ipAddress.isEmpty()) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.isEmpty()) {
      ipAddress = request.getRemoteAddr();
    }

    // MemberVO에 IP 주소 설정
    memberModel.setIp(ipAddress);*/

    // 세션 정보 저장
    session.setAttribute("loginInfo", memberModel);
    return memberModel;
  }

}
