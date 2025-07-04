package com.pcwk.ehr.cmn;

import com.google.common.base.Strings;

public class PcwkString {
	
	/**
	 * null,"" 입력되면 default value로 변경
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String nvlString(String value, String defaultValue) {
		if(Strings.isNullOrEmpty(value) == true) {
			return defaultValue;
		}
		return value;
	}
	
	/**
	 * value가 0이면 defaultValue 리턴
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int nvlZero(int value, int defaultValue) {
		return(value==0)? defaultValue:value;
		
	}
	
	/**
	 * null 또는 빈 문자열인지 체크
	 * @param str
	 * @return
	 */
	
	public static boolean isNullOrEmpty(String str) {
		return Strings.isNullOrEmpty(str);
	}

	
	// null을 빈문자열로 변환
	public static String nullToEmpty(String str) {
	//	return(str==null) ?"": str;
		return Strings.nullToEmpty(str);
		
	}


}
