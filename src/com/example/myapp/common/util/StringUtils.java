/**
 * 
 */
package com.example.myapp.common.util;

/**
 * @author Engle
 *
 */
public class StringUtils {
	private StringUtils() {
		
	}
	
	/**
	 * 返回两个String是否相等
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static Boolean equals(String str1, String str2) {
		if(str1 == null) {
			if(str2 == null) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(str1.equals(str2)) {
				return true;
			}
			return false;
		}
	}
}
