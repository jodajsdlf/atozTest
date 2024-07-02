package atoz.code.controller;

import atoz.code.model.CodeModel;
import atoz.code.model.CodeGroupModel;
import atoz.code.service.CodeService;
import atoz.member.model.MemberModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/code")
public class CodeController {

  @Autowired
  CodeService codeService;

  @RequestMapping("/codePage.do")
  public String codeGroupList(HttpServletRequest request,
                              @RequestParam(name="groupNum", required = false)Integer groupNum,
                              @RequestParam(name="codeNum", required = false)Integer codeNum,
                              Model model) throws Exception{
    HttpSession session = request.getSession();

    MemberModel loginInfo = (MemberModel) session.getAttribute("loginInfo");
    model.addAttribute("loginInfo", loginInfo);
    System.out.println("로그인 정보 로그인 정보 로그인 정보 로그인 정보");
    System.out.println(loginInfo);
    System.out.println("로그인 정보 로그인 정보 로그인 정보 로그인 정보");
    List<CodeGroupModel> codeGroupList = codeService.codeGroupList();
    model.addAttribute("codeGroupList",codeGroupList);
    model.addAttribute("groupNum", groupNum);
    model.addAttribute("codeNum", codeNum);
    return "code/code_page";
  }

  @RequestMapping("/selectCodeGroup.do")
  public ResponseEntity<?> selectCodeGroup(@RequestParam(name = "groupNum", required = false) Integer groupNum) throws Exception{
    CodeGroupModel groupModel = null;
    if(groupNum != null) {
      groupModel = codeService.selectCodeGroup(groupNum);
    }
    return ResponseEntity.ok(groupModel);
  }

  @RequestMapping("/selectCode.do")
  public ResponseEntity<?> selectCode(@RequestParam(name="codeNum", required = false) Integer codeNum) throws Exception{
    CodeModel codeModel = null;
    if(codeNum != null) {
      codeModel = codeService.selectCode(codeNum);
    }
    return ResponseEntity.ok(codeModel);
  }

  @RequestMapping("/updateCodeGroup.do")
  public String updateCodeGroup(@ModelAttribute CodeGroupModel codeGroupModel, Model model) throws Exception{
    String redirectUrl = "";
    try {
      int cnt = codeService.updateCodeGroup(codeGroupModel);
      if(cnt > 0) {
        redirectUrl = "/system/code/codePage.do?groupNum=" + codeGroupModel.getCodeGroup()+"&msg=modify";
      }
    } catch (Exception e) {
      redirectUrl = "/system/code/codePage.do?groupNum=" + codeGroupModel.getCodeGroup()+"&msg=error";
    }

    return "redirect:"+redirectUrl;
  }

  @RequestMapping("/updateCode.do")
  public String updateCode(@ModelAttribute CodeModel codeModel, Model model) throws Exception{
    System.out.println(codeModel);
    String redirectUrl = "";

    try {
      int cnt = codeService.updateCode(codeModel);
      if(cnt > 0) {
        redirectUrl = "/system/code/codePage.do?groupNum=" + codeModel.getCodeGroup() + "&codeNum=" + codeModel.getCode()+"&msg=modify";
      }
    } catch (Exception e) {
      redirectUrl = "/system/code/codePage.do?groupNum=" + codeModel.getCodeGroup() + "&codeNum=" + codeModel.getCode()+"&msg=error";
    }

    return "redirect:"+redirectUrl;
  }

  @RequestMapping("/selectCodeList.do")
  public String selectCodeList(@RequestParam(name = "groupNum", required = false) Integer groupNum,
                               @RequestParam(name="codeNum", required = false)Integer codeNum,
                               Model model) throws Exception{
    List<CodeModel> codeList = null;
    if(groupNum != null) {
      codeList = codeService.selectCodeList(groupNum);
    }
    model.addAttribute("codeNum", codeNum);
    model.addAttribute("groupNum", groupNum);
    model.addAttribute("codeList", codeList);

    return "code/code";
  }

  @RequestMapping("/deleteCodeGroup.do")
  public String deleteCodeGroup(@ModelAttribute CodeGroupModel codeGroupModel, Model model) throws Exception{
    String redirectUrl = "";
    try {
      int cnt = codeService.deleteCodeGroup(codeGroupModel);
      if(cnt > 0) {
        redirectUrl = "/system/code/codePage.do?groupNum=" + codeGroupModel.getCodeGroup()+"&msg=delete";
      }else {
        redirectUrl = "/system/code/codePage.do?groupNum=" + codeGroupModel.getCodeGroup()+"&msg=error";
      }

    }catch (Exception e) {
      redirectUrl = "/system/code/codePage.do?groupNum=" + codeGroupModel.getCodeGroup()+"&msg=error";
    }
    return "redirect:"+redirectUrl;
  }

  @RequestMapping("/deleteCode.do")
  public String deleteCode(@ModelAttribute CodeModel codeModel, Model model) throws Exception{
    String redirectUrl = "";
    try {

      int cnt = codeService.deleteCode(codeModel);
      if(cnt > 0) {
        redirectUrl = "/system/code/codePage.do?groupNum=" + codeModel.getCodeGroup() + "&codeNum=" + codeModel.getCode()+"&msg=delete";
      }else {
        redirectUrl = "/system/code/codePage.do?groupNum=" + codeModel.getCodeGroup() + "&codeNum=" + codeModel.getCode()+"&msg=error";
      }

    }catch (Exception e) {
      redirectUrl = "/system/code/codePage.do?groupNum=" + codeModel.getCodeGroup() + "&codeNum=" + codeModel.getCode()+"&msg=error";
    }
    return "redirect:"+redirectUrl;
  }

  @RequestMapping("/insertCodeGroup.do")
  public String insertCodeGroup(@ModelAttribute CodeGroupModel codeGroupModel,  Model model) throws Exception{
    String redirectUrl = "";
    try {
      int cnt = codeService.insertCodeGroup(codeGroupModel);

      if(cnt > 0) {
        redirectUrl = "/system/code/codePage.do?msg=save";
      }
    } catch (Exception e) {
      redirectUrl = "/system/code/codePage.do?msg=error";
    }

    return "redirect:"+redirectUrl;
  }

  @RequestMapping("/insertCode.do")
  public String insertCode(@ModelAttribute CodeModel codeModel, Model model) throws Exception{
    String redirectUrl = "";
    try {
      int cnt = codeService.insertCode(codeModel);
      if(cnt > 0) {
        redirectUrl = "/system/code/codePage.do?groupNum=" + codeModel.getCodeGroup() + "&msg=save";
      }
    } catch (Exception e) {
      redirectUrl = "/system/code/codePage.do?groupNum=" + codeModel.getCodeGroup() + "&msg=error";
    }

    return "redirect:"+redirectUrl;
  }

  @PostMapping("/sortCode.do")
  public ResponseEntity<String> sortCode(@RequestBody Map<String, Object> requestBody) {
    try {
      List<Map<String, Object>> sortedData = (List<Map<String, Object>>) requestBody.get("sortedData");
      System.out.println(sortedData.toString());

      for (Map<String, Object> item : sortedData) {
        // 코드와 정렬값의 타입을 확인하고 적절한 타입으로 캐스팅
        Object codeObj = item.get("code");
        Object sortObj = item.get("sort");
        System.out.println(codeObj);
        System.out.println(sortObj);

        if (codeObj instanceof Integer && sortObj instanceof Integer) {
          int code = (int) codeObj;
          int sort = (int) sortObj;
          System.out.println("Code: " + code + ", Sort: " + sort);
          codeService.sortCode(code, sort); // 수신한 데이터를 이용한 작업 수행
        } else {
          // 적절한 타입으로 변환할 수 없는 경우 에러 처리
          System.out.println("Invalid data type for code or sort");
          return new ResponseEntity<>("작업 실패 - 데이터 형식 오류", HttpStatus.BAD_REQUEST);
        }
      }

      return new ResponseEntity<>("작업 성공", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("작업 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
