package atoz.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeGroupModel {
  private String codeGroupName;
  private int codeGroup;
  private String note;
  private long comNumber;
  private List<CodeModel> codeList;
}
