package atoz.code.service;

import atoz.code.mapper.CodeMapper;
import atoz.code.model.CodeGroupModel;
import atoz.code.model.CodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {

  @Autowired
  CodeMapper mapper;


  public List<CodeGroupModel> codeGroupList() throws Exception {
    return mapper.codeGroupList();
  }

  public CodeGroupModel selectCodeGroup(int groupNum) throws Exception {
    return mapper.selectCodeGroup(groupNum);
  }

  public List<CodeModel> selectCodeList(int groupNum) throws Exception {
    return mapper.selectCodeList(groupNum);
  }

  public CodeModel selectCode(int codeNum) throws Exception {
    return mapper.selectCode(codeNum);
  }

  public int updateCodeGroup(CodeGroupModel model) throws Exception {
    return mapper.updateCodeGroup(model);
  }

  public int updateCode(CodeModel model) throws Exception {
    return mapper.updateCode(model);
  }

  public int insertCodeGroup(CodeGroupModel model) throws Exception {
    return mapper.insertCodeGroup(model);
  }

  public int insertCode(CodeModel model) throws Exception {
    return mapper.insertCode(model);
  }

  public int deleteCodeGroup(CodeGroupModel model) throws Exception {
    return mapper.deleteCodeGroup(model);
  }

  public int deleteCode(CodeModel model) throws Exception {
    return mapper.deleteCode(model);
  }

  public int sortCode(int code, int sort) throws Exception {
    return mapper.sortCode( code,  sort);
  }
}
