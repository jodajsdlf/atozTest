package atoz.member.service.impl;

import atoz.member.model.LoginModel;
import atoz.member.model.MemberModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
  MemberModel login(LoginModel loginModel);
}
