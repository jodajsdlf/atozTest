package atoz.code.mapper;

import atoz.code.model.CodeGroupModel;
import atoz.code.model.CodeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeMapper {
  List<CodeGroupModel> codeGroupList();
  CodeGroupModel selectCodeGroup(int groupNum);
  List<CodeModel> selectCodeList(int groupNum);
  int insertCodeGroup(CodeGroupModel model);
  int insertCode(CodeModel model);
  int updateCodeGroup(CodeGroupModel model);
  int deleteCodeGroup(CodeGroupModel model);
  CodeModel selectCode(int codeNum);
  int updateCode(CodeModel model);
  int deleteCode(CodeModel model);
  int sortCode(@Param("code") int code, @Param("sort") int sort) throws Exception;
}
