package atoz.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeModel {
  private int code;
  private String codeName;
  private String note;
  private int codeGroup;
}
