package com.hsuforum.common.web.util;

/**
 * String utility
 * 
 * @author Marvin
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/**
	 * StringUtils.getIntMoneyStr("001234567") = "1,234,567"
	 *
	 * @param sInt
	 * @return
	 */
	public static String getIntMoneyStr(String sInt) {

		if (isBlank(sInt)) {
			return "";
		}

		sInt = sInt.trim();

		StringBuffer sb = new StringBuffer();

		sInt = trimLeftZero(sInt);
		int iLen = sInt.length();

		for (int i = 0; i < iLen; i++) {

			char ch = sInt.charAt(i);

			sb.append(ch);

			int iRemainLen = iLen - i - 1;

			if ((iRemainLen > 0) && (iRemainLen % 3 == 0)) {
				sb.append(",");
			}

		}
		return sb.toString();

	}

	/**
	 * StringUtils.getMoneyStr(null) = "" StringUtils.getMoneyStr(" ") = ""
	 * StringUtils.getMoneyStr("1234567.89") = "1,234,567.89"
	 * StringUtils.getMoneyStr("1234567.00") = "1,234,567";
	 * StringUtils.getMoneyStr("1234567.0100") = "1,234,567.01";
	 *
	 * @param sMoney
	 * @return
	 */
	public static String getMoneyStr(String sMoney) {

		if (isBlank(sMoney)) {
			return "";
		}

		sMoney = sMoney.trim();

		StringBuffer sb = new StringBuffer();
		int iLen = sMoney.length();

		if (sMoney.startsWith("+") || sMoney.startsWith("-")) {

			String sSign = substring(sMoney, 0, 1);

			sb.append(sSign.equals("-") ? sSign : "");
			sMoney = substring(sMoney, 1);
		}

		String sInt = "";

		String sDecimal = "";

		String sDot = "";

		int index = sMoney.indexOf(".");

		if (index >= 0) {
			sDot = ".";
			sInt = sMoney.substring(0, index);
			if ((index + 1) < iLen) {
				sDecimal = sMoney.substring(index + 1, sMoney.length());
			}
		} else {
			sInt = sMoney;
		}

		sInt = getIntMoneyStr(sInt);

		sDecimal = trimRightZero(sDecimal);

		if (sInt.length() > 0) {
			sb.append(sInt);

			if (sDecimal.length() > 0) {
				sb.append(sDot).append(sDecimal);
			}
		}

		else {
			if (sDecimal.length() > 0) {
				sb.append("0.").append(sDecimal);
			} else {
				sb.append("0");
			}
		}

		return sb.toString();
	}

	/**
	 * StringUtils.getMoneyStr(null, 1) = ""
	 * StringUtils.getMoneyStr(" ", 2) = "" 
	 * StringUtils.getMoneyStr("1234567.89", 2) = "1,234,567.89"
	 * StringUtils.getMoneyStr("1234567.00", 1) = "1,234,567.0";
	 * StringUtils.getMoneyStr("1234567.0100", 2) = "1,234,567.01";
	 *
	 * @param sMoney
	 * @param iScale
	 * @return
	 */
	public static String getMoneyStr(String sMoney, int iScale) {

		if (isBlank(sMoney)) {
			return "";
		}

		sMoney = sMoney.trim();

		StringBuffer sb = new StringBuffer();
		int iLen = sMoney.length();

		if (sMoney.startsWith("+") || sMoney.startsWith("-")) {

			String sSign = substring(sMoney, 0, 1);

			sb.append(sSign.equals("-") ? sSign : "");
			sMoney = substring(sMoney, 1);
		}

		String sInt = "";

		String sDecimal = "";

		String sDot = "";

		int index = sMoney.indexOf(".");

		if (index >= 0) {
			sDot = ".";
			sInt = sMoney.substring(0, index);
			if ((index + 1) < iLen) {
				sDecimal = sMoney.substring(index + 1, sMoney.length());
			}
		} else {
			sInt = sMoney;
		}

		sInt = getIntMoneyStr(sInt);

		sDecimal = substring(sDecimal, 0, iScale);

		if (sInt.length() > 0) {
			sb.append(sInt);

			if (sDecimal.length() > 0) {
				sb.append(sDot).append(rightPad(sDecimal, iScale, "0"));
			} else {
				if (iScale > 0) {
					sb.append(".").append(rightPad(sDecimal, iScale, "0"));
				}
			}
		}

		else {
			if (sDecimal.length() > 0) {
				sb.append("0.").append(rightPad(sDecimal, iScale, "0"));
			} else {
				if (iScale > 0) {
					sb.append("0.").append(rightPad(sDecimal, iScale, "0"));
				} else {
					sb.append("0");
				}
			}
		}

		return sb.toString();
	}

	public static int str2Int(String sValue) {
		return StringUtils.str2Int(sValue, 0);
	}

	/**
	 * 
	 * @param sValue
	 * @param iDefaultValue
	 * @return
	 */
	public static int str2Int(String sValue, int iDefaultValue) {
		int iValue = iDefaultValue;
		try {
			iValue = Integer.parseInt(sValue);
		} catch (Exception e) {
			iValue = iDefaultValue;
		}

		return iValue;
	}

	/**
	 * StringUtils.trimLeft(" 345600") = "345600"<br>
	 * StringUtils.trimLeft(" 345600 ") = "345600 "
	 *
	 * @param sSource
	 * @return
	 */
	public static String trimLeft(String sSource) {

		if (sSource == null) {
			return "";
		}

		int iLen = sSource.length();
		int index = -1;
		for (int i = 0; (i < iLen && index < 0); i++) {
			char ch = sSource.charAt(i);

			if (ch != ' ') {
				index = i;
			}
		}
		String s = "";

		if (index >= 0) {
			s = sSource.substring(index, iLen);
		}

		return s;

	}

	/**
	 * StringUtils.trimLeftZero("0012345600") = "12345600"
	 *
	 * @param sSource
	 * @return
	 */
	public static String trimLeftZero(String sSource) {

		if (sSource == null) {
			return "";
		}

		int iLen = sSource.length();
		int index = -1;
		for (int i = 0; (i < iLen && index < 0); i++) {
			char ch = sSource.charAt(i);

			if (ch != '0') {
				index = i;
			}
		}
		String s = "";

		if (index >= 0) {
			s = sSource.substring(index, iLen);
		}

		return s;

	}

	/**
	 * StringUtils.trimRight("1234500 ") = "012345"<br>
	 * StringUtils.trimRight(" 1234500 ") = " 012345"
	 *
	 * @param sSource
	 * @return
	 */
	public static String trimRight(String sSource) {

		if (sSource == null) {
			return "";
		}

		int iLen = sSource.length();
		int index = -1;

		for (int i = (iLen - 1); (i >= 0 && index < 0); i--) {
			char ch = sSource.charAt(i);
			if (ch != ' ') {
				index = i;
			}
		}

		String s = "";
		if (index >= 0) {
			s = sSource.substring(0, index + 1);
		}

		return s;
	}

	/**
	 * StringUtils.trimRightZero("01234500") = "012345"
	 *
	 * @param sSource
	 * @return
	 */
	public static String trimRightZero(String sSource) {

		if (sSource == null) {
			return "";
		}

		int iLen = sSource.length();
		int index = -1;

		for (int i = (iLen - 1); (i >= 0 && index < 0); i--) {
			char ch = sSource.charAt(i);
			if (ch != '0') {
				index = i;
			}
		}

		String s = "";
		if (index >= 0) {
			s = sSource.substring(0, index + 1);
		}

		return s;
	}

	public static String trimZero(String sRate) {

		if (sRate == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		int iLen = sRate.length();

		String sInt = "";

		String sDecimal = "";

		String sDot = "";

		int index = sRate.indexOf(".");

		if (index >= 0) {
			sDot = ".";
			sInt = sRate.substring(0, index);
			if ((index + 1) < iLen) {
				sDecimal = sRate.substring(index + 1, sRate.length());
			}
		} else {
			sInt = sRate;
		}

		sInt = trimLeftZero(sInt);

		sDecimal = trimRightZero(sDecimal);

		if (sInt.length() > 0) {
			sb.append(sInt);

			if (sDecimal.length() > 0) {
				sb.append(sDot).append(sDecimal);
			}
		}

		else {
			if (sDecimal.length() > 0) {
				sb.append("0.").append(sDecimal);
			} else {
				sb.append("0.0");
			}
		}

		return sb.toString();
	}
}
