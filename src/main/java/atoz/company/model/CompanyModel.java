package atoz.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyModel {
  private int num;
  private long com_number;
  private String com_name;
  private int userCnt;
  private int useBeaconCnt;
  private int used;
  private String note;
  private String com_ci;
  private String ceo_name;
  private String ceo_phone;
  private String ceo_email;
  private String address1;
  private String address2;
  private String admin_name;
  private String admin_id;
  private String admin_pw;

}
