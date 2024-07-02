package atoz.cmm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationForm {

	private static final Logger logger = LoggerFactory.getLogger(ValidationForm.class);
	 
	//html테그를 제거하고 반환합니다.
	public static String removeTag(String input) {
	    String regex = "<[^>]*>";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(input);
	    return matcher.replaceAll("");
	}
	
	//문자열에 포함된 공백 문자 이외의 문자가 없을 경우에는 true를 반환
	public static boolean validEmpty(String input)
	{
		String str =input.replaceAll("\\p{Z}", "");
		int length = str.length();
		
		if(length==0)
		{
			return true;
		}
		return false;
	}
	
	//문자열의 최소,최대 값 확인후 true 반환
	public static boolean validLength(int min,int max,String input)
	{
		if((input.length()<min) || (input.length() > max))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	//문자열이 한글로만 이루어 졌을때 true 반환
	public static boolean validKorean(String input)
	{      		
		Pattern p = Pattern.compile("^[가-힣]*$");
		Matcher m = p.matcher(input);
		
		if(m.find())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//문자열이 영어로만 이루어 졌을때 true 반환
	public static boolean validIncludeEng(String input)
	{
		Pattern p = Pattern.compile("^(?=.*?[a-zA-Z]).*$");
		Matcher m = p.matcher(input);
		if(m.find())
		{
			return true;
		}
		return false;
	}

	//숫자 포함여부 확인 (숫자 포함시 true)
	public static boolean validIncludeNum(String input)
	{
		Pattern p = Pattern.compile("^(?=.*?[0-9]).*$");
		Matcher m = p.matcher(input);
		if(m.find())
		{
			return true;
		}
		return false;
	}

	//특수문자 사용 확인 (특수문자 없으면 true)
	public static boolean validNoSpecial(String input)
	{
		Pattern p = Pattern.compile("^[a-zA-Z0-9가-힣]*$");
		Matcher m = p.matcher(input);
		
		if(m.find())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// 중국어 등 제 3국 문자를 포함하고 있으면 true 반환
	public static boolean validChinaChar(String input)
	{
		Pattern	p = Pattern.compile("^[가-힣a-zA-Z0-9`~!@#$%^&*()-=_+\\[\\]{}:;',./<>?\\\\|]*$");
		Matcher m = p.matcher(input);
		m = p.matcher(input);
		if(m.find())
		{
			return false;
		}
		return true;
	}

	// 숫자로만 이루어져 있으면 ture 반환
	public static boolean validNum(String input)
	{
		if(input.equals(""))
		{
			return false;
		}
		
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher m = p.matcher(input);
		
		if(m.find())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// 영문,숫자로만 이루어져 있으면 ture 반환
	public static boolean validEngNum(String input)
	{
		Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
		Matcher m = p.matcher(input);
		
		if(m.find())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//같은 문자가 5번 반복되면 false 반환
	public static boolean validContinueChar(String input)
	{
		Pattern p = Pattern.compile("(\\w)\\1\\1\\1\\1");
		Matcher m = p.matcher(input);
		if(m.find())
		{
			return false;
		}
		return true;
	}
	
	//특수문자, 숫자, 영문 포함 시 ture 반환
	public static boolean validCombineChar(String input)
	{
		boolean a1 = validNoSpecial(input);
		boolean a2 = validIncludeNum(input);
		boolean a3 = validIncludeEng(input);
		if(a1==false&&a2==true&&a3==true)
		{
			return true;
		}
		return false;
	}

	//비밀번호 유효성 확인
	public static boolean validPassword(String input,String input2,int min,int max)
	{
		String str =input.replaceAll("\\p{Z}", "");
		String str2 =input2.replaceAll("\\p{Z}", "");
		int length = str.length();
		int length2 = input.length();
		
		if(str.length() <= 0)
		{
			return false;
		}
		
		
		if((str.length()<min) || (str.length() > max))
		{
			return false;
		}
		
		if(length!=length2)
		{
			return false;
		}
		
		Pattern p = Pattern.compile("^*&");
		Matcher m = p.matcher(input);
		if(m.find())
		{
			return false;
		}
		
		if(validChinaChar(input))
		{
			return false;
		}

		p = Pattern.compile("(\\w)\\1\\1\\1\\1");
		m = p.matcher(input);
		if(m.find())
		{
			return false;
		}
		if(!validCombineChar(input))
		{
			return false;
		}
		if((str.length() > 0)&&(str2.length() <= 0))
		{
			return false;
		}
		
		if(!str.equals(str2))
		{
			return false;
		}
		
		return true;
	}

	//전화번호 유효성 확인 02-1234-1234
	public static boolean validPhone(String input)
	{
		Pattern p = Pattern.compile("^\\d{2,3}\\d{3,4}\\d{4}$");
		Matcher m = p.matcher(input);
		if(m.find())
		{
			return true;
		}
		else
		{
			logger.error("유효하지 않은 전화번호:"+input);
			return false;
		}
	}
	
	//이메일 유효성 확인
	public static boolean validEmail(String input)
	{
		Pattern p = Pattern.compile("^[-!#$%&'*+./0-9=?A-Z^_a-z{|}~]+@[-!#$%&'*+/0-9=?A-Z^_a-z{|}~]+.[-!#$%&'*+./0-9=?A-Z^_a-z{|}~]+$");
		Matcher m = p.matcher(input);
		if(m.find())
		{
			return true;
		}	
		else
		{
			logger.error("유효하지 않은 이메일:"+input);
			return false;	
		}
	}
	
	//주민등록번호 유효성체크
	public static boolean validPersonNumber(String input) throws Exception{
        if (input.length() != 13)
        {
        	return false;
        }
 
        String leftSid = input.substring(0, 6);
        String rightSid = input.substring(6, 13);
 
        int yy = Integer.parseInt(leftSid.substring(0, 2));
        int mm = Integer.parseInt(leftSid.substring(2, 4));
        int dd = Integer.parseInt(leftSid.substring(4, 6));
        
        if (yy > 99 || mm > 12 || mm < 1 || dd < 1 || dd > 31) return false;
 
        int digit1 = Integer.parseInt(leftSid.substring(0, 1)) * 2;
        int digit2 = Integer.parseInt(leftSid.substring(1, 2)) * 3;
        int digit3 = Integer.parseInt(leftSid.substring(2, 3)) * 4;
        int digit4 = Integer.parseInt(leftSid.substring(3, 4)) * 5;
        int digit5 = Integer.parseInt(leftSid.substring(4, 5)) * 6;
        int digit6 = Integer.parseInt(leftSid.substring(5, 6)) * 7;
 
        int digit7 = Integer.parseInt(rightSid.substring(0, 1)) * 8;
        int digit8 = Integer.parseInt(rightSid.substring(1, 2)) * 9;
        int digit9 = Integer.parseInt(rightSid.substring(2, 3)) * 2;
        int digit10 = Integer.parseInt(rightSid.substring(3, 4)) * 3;
        int digit11 = Integer.parseInt(rightSid.substring(4, 5)) * 4;
        int digit12 = Integer.parseInt(rightSid.substring(5, 6)) * 5;
 
        int last_digit = Integer.parseInt(rightSid.substring(6, 7));
 
        int error_verify = (digit1 + digit2 + digit3 + digit4 + digit5 + digit6 + digit7 + digit8 + digit9 + digit10 + digit11 + digit12) % 11;
 
        int sum_digit = 0;
        if (error_verify == 0) {
            sum_digit = 1;
        } else if (error_verify == 1) {
            sum_digit = 0;
        } else {
            sum_digit = 11 - error_verify;
        }
 
        if (last_digit == sum_digit)
        {
        	return true;
        }
        else
        {
        	return false;
        }
		
	}
}
