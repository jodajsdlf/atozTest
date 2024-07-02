package atoz.company.controller;

import atoz.company.model.CompanyModel;
import atoz.company.service.CompanyService;
import atoz.member.model.MemberModel;
import atoz.page.Criteria;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/system/company")
public class CompanyController {

  @Autowired
  CompanyService companyService;

  private String UPLOAD_DIR ="/home/atoz/test/";

  @RequestMapping("/companyList.do")
  public String companyList(HttpServletRequest request, Criteria cri, @RequestParam(name="num", required = false)Integer num, Model model) throws Exception{
    HttpSession session = request.getSession();

    MemberModel loginInfo = (MemberModel) session.getAttribute("loginInfo");
    model.addAttribute("loginInfo", loginInfo);
    List<CompanyModel> companyList= companyService.selectCompanyList();
    model.addAttribute("companyList", companyList);
    System.out.println(companyList);

        return "/company/company";
  }

  @RequestMapping("/companyInsert.do")
  public String CompanyInsert() throws Exception{
    return "/company/company_insert";
  }

  @PostMapping("/insertCompany.do")
  public String insertCompany(@RequestParam(name = "imgFile", required = false) MultipartFile file, @ModelAttribute CompanyModel companyModel, @ModelAttribute Criteria cri, Model model) throws Exception{
    int cnt = 0;
    String redirectUrl = "";
    try {
      if(file != null && !file.isEmpty()) {
        String filePath = file.getOriginalFilename();
        int index = filePath.lastIndexOf('.');
        String extension = filePath.substring(index);
        String uuid = UUID.randomUUID().toString();

        companyModel.setCom_ci(uuid+extension);
        cnt = companyService.insertComapny(companyModel);
        File dir = new File(UPLOAD_DIR);

        if(!dir.exists()) {
          dir.mkdirs();
        }
        Path path = Paths.get(UPLOAD_DIR, uuid+extension);

        Files.write(path, file.getBytes());

      }

      if(cnt > 0) {
        redirectUrl = "/system/company/companyList.do?msg=save";
      }
    }catch (Exception e) {
      redirectUrl = "/system/company/companyList.do?msg=error";
    }
    return "redirect:"+redirectUrl;
  }

  @RequestMapping("/selectCompany.do")
  public String selectCompany(@RequestParam("Num") int num, Model model) throws Exception{
    System.out.println("beacon detail number : " + num);
    CompanyModel companyModel = companyService.selectCompany(num);
    System.out.println("컴퍼니 모델 컴퍼니 모델 컴퍼니 모델 컴퍼니 모델 컴퍼니 모델 컴퍼니 모델 ");
    System.out.println(companyModel);
    System.out.println("컴퍼니 모델 컴퍼니 모델 컴퍼니 모델 컴퍼니 모델 컴퍼니 모델 컴퍼니 모델 ");
    byte[] imageBytes = null;
    String encodeImage = null;
    if(companyModel .getCom_ci() != null) {
      String imagePath = UPLOAD_DIR+companyModel.getCom_ci();
      File imageFile = new File(imagePath);
      if(imageFile.exists()) {
        imageBytes = Files.readAllBytes(imageFile.toPath());
        encodeImage = java.util.Base64.getEncoder().encodeToString(imageBytes);
      }
    }
    model.addAttribute("company", companyModel);
    model.addAttribute("image", encodeImage);
    return "/company/company_select";
  }

  @PostMapping("/updateCompany.do")
  public String updateCompany(@RequestParam(name = "num") int num, @RequestParam(name = "imgFile", required = false) MultipartFile file, @ModelAttribute CompanyModel companyModel,@ModelAttribute Criteria cri, Model model) throws Exception{
    int cnt = 0, cnt2=1;
    String redirectUrl = "";
    String encodedSearchName = URLEncoder.encode(cri.getSearchName(), "UTF-8");
    companyModel.setNum(num);
    System.out.println("getNum getNum getNum getNum getNum getNum getNum ");
    System.out.println(companyModel.getNum());
    System.out.println("getNum getNum getNum getNum getNum getNum getNum ");
    try {
      if(file != null && !file.isEmpty()) {
        CompanyModel selectCompanyModel = companyService.selectCompany(companyModel.getNum());
        String image = selectCompanyModel.getCom_ci();
        File imagePath = new File(UPLOAD_DIR+image);
        if(image != null && imagePath.exists()) {
          Files.deleteIfExists(imagePath.toPath());
        }

        String filePath = file.getOriginalFilename();
        int index = filePath.lastIndexOf('.');
        String extension = filePath.substring(index);
        String uuid = UUID.randomUUID().toString();

        companyModel.setCom_ci(uuid+extension);
        cnt = companyService.updateCompanyAndImg(companyModel);
        File dir = new File(UPLOAD_DIR);

        if(!dir.exists()) {
          dir.mkdirs();
        }
        Path path = Paths.get(UPLOAD_DIR, uuid+extension);

        Files.write(path, file.getBytes());

      }else {
        System.out.println("companyModel companyModel companyModel companyModel ");
        System.out.println(companyModel);
        System.out.println("companyModel companyModel companyModel companyModel ");
        cnt = companyService.updateCompany(companyModel);

      }

      if(cnt > 0 && cnt2 > 0) {
        redirectUrl = "/system/company/selectCompany.do?Num=" + companyModel.getNum();
        /*redirectUrl = "/system/company/selectCompany.do?Num=" + cri.getPageNum() + "&amount=" + cri.getAmount() + "&searchType=" + cri.getSearchType() + "&searchName=" + encodedSearchName + "&num=" + companyDTO.getNum()+"&msg=modify";*/
      }
    }catch (Exception e) {
      redirectUrl = "/system/company/selectCompany.do?Num=" + companyModel.getNum();
      /*redirectUrl = "/system/company/selectCompany.do?Num=" + cri.getPageNum() + "&amount=" + cri.getAmount() + "&searchType=" + cri.getSearchType() + "&searchName=" + encodedSearchName + "&num=" + companyDTO.getNum()+"&msg=error";*/
    }
    return "redirect:"+redirectUrl;
  }

  @PostMapping("/deleteCompany.do")
  public String deleteCompany(@RequestParam(name = "num") int num,@ModelAttribute Criteria cri) throws Exception{
    int cnt = 0;
    String redirectUrl = "";
    String encodedSearchName = URLEncoder.encode(cri.getSearchName(), "UTF-8");
    System.out.println("companyModel companyModel companyModel companyModel ");
    System.out.println(num);
    System.out.println("companyModel companyModel companyModel companyModel ");
    try {
      cnt = companyService.deleteCompany(num);
      if(cnt > 0) {
        redirectUrl = "/system/company/companyList.do?pageNum=" + cri.getPageNum() + "&amount=" + cri.getAmount() + "&searchType=" + cri.getSearchType() + "&searchName=" + encodedSearchName +"&msg=delete";
      }
    }catch (Exception e) {
      redirectUrl = "/system/company/companyList.do?pageNum=" + cri.getPageNum() + "&amount=" + cri.getAmount() + "&searchType=" + cri.getSearchType() + "&searchName=" + encodedSearchName +"&msg=delete_error";
    }
    return "redirect:"+redirectUrl;
  }

}
