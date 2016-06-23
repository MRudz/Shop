package by.epam.shop.resource;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordCoding {
		public static String md5Coding(String password)
		{	String result = null;
			result = DigestUtils.md5Hex(password);
			System.out.println(result);
			return result;
		}
}
