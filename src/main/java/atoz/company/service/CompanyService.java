package atoz.company.service;

import atoz.company.mapper.CompanyMapper;
import atoz.company.model.CompanyModel;
import atoz.page.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

  @Autowired
  CompanyMapper mapper;

  public List<CompanyModel> selectCompanyList() throws Exception{
    return mapper.selectCompanyList();
  }

  public int insertComapny(CompanyModel companyModel) throws Exception{
    return mapper.insertComapny(companyModel);
  }

  public CompanyModel selectCompany(int num) throws Exception {
    return mapper.selectCompany(num);
  }

  public int updateCompanyAndImg(CompanyModel model) throws Exception {
    return mapper.updateCompanyAndImg(model);
  }

  public int updateCompany(CompanyModel model) {
    return mapper.updateCompany(model);
  }

  public int deleteCompany(int num) {
    return mapper.deleteCompany(num);
  }
}
