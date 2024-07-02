package atoz.cmm;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileDownload {
	String UPLOAD_DIR = "/home/atoz/test/";
	
	// 파일 반환
		@RequestMapping(value = "image.do")
		public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String filename = request.getParameter("fileName");
			String realFilename = "";
			System.out.println(filename);

			try {
				String browser = request.getHeader("User-Agent");
				// 파일 인코딩
				if (browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {
					filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
				} else {
					filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
				}
			} catch (UnsupportedEncodingException e) {
				System.out.println("UnsupportedEncodingException 발생");
			}

			realFilename = UPLOAD_DIR + filename;
			File file = new File(realFilename);
			if (!file.exists()) {
				realFilename = UPLOAD_DIR + "noimg.png";
			}
			//System.out.println("retrunImage: "+ realFilename);

			// 파일명 지정
			response.setContentType("application/octer-stream");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

			try {
				OutputStream os = response.getOutputStream();
				FileInputStream fis = new FileInputStream(realFilename);
				int cnt = 0;
				byte[] bytes = new byte[512];
				while ((cnt = fis.read(bytes)) != -1) {
					os.write(bytes, 0, cnt);
				}
				fis.close();
				os.close();
			} catch (Exception e) {
				//e.printStackTrace();
				OutputStream os = response.getOutputStream();
				FileInputStream fis = new FileInputStream(UPLOAD_DIR +"noimg.png");
				int cnt = 0;
				byte[] bytes = new byte[512];
				while ((cnt = fis.read(bytes)) != -1) {
					os.write(bytes, 0, cnt);
				}
				fis.close();
				os.close();
			}

		}
}
