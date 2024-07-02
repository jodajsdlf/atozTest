package atoz.company.mapper;

import atoz.company.model.CompanyModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {
  List<CompanyModel> selectCompanyList();
  int insertComapny(CompanyModel companyModel);
  CompanyModel selectCompany(int num);
  int updateCompanyAndImg(CompanyModel model);
  int updateCompany(CompanyModel model);
  int deleteCompany(int num);
}
