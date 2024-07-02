package atoz.cmm.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorVO {
	private String redirectUrl;
	private String title;
	private String msg;

}
