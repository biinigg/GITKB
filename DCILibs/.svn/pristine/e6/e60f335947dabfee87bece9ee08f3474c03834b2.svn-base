package com.dci.jweb.PublicLib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import com.dci.jweb.DCIConstants.DCIWebConstants;

public class DCIString {
	public static boolean isNullOrEmpty(String str) {
		boolean isnull = false;

		if (str == null) {
			isnull = true;
		} else {
			if (str.trim().length() == 0) {
				isnull = true;
			}
		}

		return isnull;
	}

	public static boolean isNullOrEmpty(Object str) {
		boolean isnull = false;

		if (str == null) {
			isnull = true;
		} else {
			if (str.toString().trim().length() == 0) {
				isnull = true;
			}
		}

		return isnull;
	}

	public static String strFix(String str, int length, boolean fixLeft, String fixChar) {
		String tmp = null;

		tmp = str;

		for (int i = 0; i < length - str.length(); i++) {
			if (fixLeft) {
				tmp = fixChar + tmp;
			} else {
				tmp = tmp + fixChar;
			}
		}

		return tmp;
	}

	public static String Base64Encode(String str) {
		String code = null;
		try {
			if (str != null && !str.equals("")) {
				Base64 b64 = new Base64();
				code = new String(b64.encode(str.getBytes()));
			}
		} catch (Exception ex) {
			code = null;
			ex.printStackTrace();
		}
		return code;
	}

	public static String Base64Decode(String code) {
		String str = null;
		try {
			if (code != null && !code.equals("")) {
				Base64 b64 = new Base64();
				str = new String(b64.decode(code.getBytes()));
			}
		} catch (Exception ex) {
			str = null;
			ex.printStackTrace();
		}
		return str;
	}

	public static String strDecode(String value) {
		String result = null;
		String tmp = null;
		byte[] tmparr = null;
		ArrayList<Byte> arr = null;

		try {
			if (DCIString.isNullOrEmpty(value)) {
				result = value;
			} else {
				tmp = DCIString.Base64Decode(value);
				arr = new ArrayList<Byte>();
				while (tmp.length() - DCIWebConstants.getKeylength() >= 0) {
					if (tmp.length() <= 4) {
						arr.add(Byte.parseByte(tmp));
						tmp = "";
					} else {
						if (tmp.startsWith("-")) {
							arr.add(Byte.parseByte(tmp.substring(0, DCIWebConstants.getKeylength() + 1)));
							tmp = tmp.substring(DCIWebConstants.getKeylength() + 1);
						} else {
							arr.add(Byte.parseByte(tmp.substring(0, DCIWebConstants.getKeylength())));
							tmp = tmp.substring(DCIWebConstants.getKeylength());
						}
					}
				}

				tmparr = new byte[arr.size()];

				for (int i = 0; i < arr.size(); i++) {
					tmparr[i] = arr.get(i);
				}

				result = new String(tmparr);
			}
		} catch (Exception ex) {
			result = value;
		}

		return result;
	}

	public static String strEncode(String value) {
		String result = null;
		byte[] tmparr = null;
		String tmp = null;
		try {
			if (DCIString.isNullOrEmpty(value)) {
				result = value;
			} else {
				tmparr = value.getBytes();

				for (int i = 0; i < tmparr.length; i++) {
					if (Integer.parseInt(String.valueOf(tmparr[i])) < 0) {
						if (i == 0) {
							tmp = "-"
									+ DCIString.strFix(String.valueOf(tmparr[i]).substring(1),
											DCIWebConstants.getKeylength(), true, "0");
						} else {
							tmp += "-"
									+ DCIString.strFix(String.valueOf(tmparr[i]).substring(1),
											DCIWebConstants.getKeylength(), true, "0");
						}
					} else {
						if (i == 0) {
							tmp = DCIString.strFix(String.valueOf(tmparr[i]), DCIWebConstants.getKeylength(),
									true, "0");
						} else {
							tmp += DCIString.strFix(String.valueOf(tmparr[i]),
									DCIWebConstants.getKeylength(), true, "0");
						}
					}
				}

				result = DCIString.Base64Encode(tmp);
			}
		} catch (Exception ex) {
			result = value;
		}

		return result;
	}

	public static boolean isInteger(String value) {
		boolean result = false;

		try {
			Integer.parseInt(value);

			result = true;
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

	public static boolean isNumber(String value) {
		boolean result = false;

		try {
			result = isInteger(value);
			if (!result) {
				result = isLong(value);
				if (!result) {
					Double.parseDouble(value);
					result = true;
				}
			}
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

	private static boolean isLong(String value) {
		boolean result = false;

		try {
			Long.parseLong(value);

			result = true;
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

	public static String numFormat(String value, String decimals) {
		String result = null;
		String oriValue = null;
		int decis = 0;
		try {
			if (isNullOrEmpty(value)) {
				oriValue = "";
			} else {
				oriValue = value;
				if (isNumber(value)) {
					if (isNullOrEmpty(decimals)) {
						decimals = "0";
					}
					if (!isInteger(decimals)) {
						if (value.indexOf(".") == -1) {
							result = String.format("%,d", Integer.parseInt(value));
						} else {
							decis = value.length() - (value.indexOf(".") + 1);
							result = String.format("%,." + decis + "f", Double.parseDouble(value));
						}
					} else {
						decis = Integer.parseInt(decimals);

						if (decis <= 0) {
							if (value.indexOf(".") != -1) {
								value = value.substring(0, value.indexOf("."));
							}
							result = String.format("%,d", Integer.parseInt(value));
						} else {
							if (value.indexOf(".") == -1) {
								value = value + ".0";
							}
							result = String.format("%,." + decis + "f", Double.parseDouble(value));
						}
					}
				}
			}
		} catch (Exception ex) {
			result = oriValue;
		} finally {
			if (result == null) {
				result = oriValue;
			}
		}

		return result;
	}

	public static String dateFormat(String value, String targetFormat) {
		String result = null;
		DateFormat dateFormat = null;
		DateFormat outputFormat = null;
		Date date = null;
		try {
			if (isNullOrEmpty(value)) {
				result = "";
			} else if (isNullOrEmpty(targetFormat)) {
				dateFormat = new SimpleDateFormat(sourceFormat(value));
				date = dateFormat.parse(value);
				outputFormat = new SimpleDateFormat("yyyy-MM-dd");
				result = outputFormat.format(date);
			} else {
				dateFormat = new SimpleDateFormat(sourceFormat(value));
				date = dateFormat.parse(value);
				outputFormat = new SimpleDateFormat(targetFormat);

				result = outputFormat.format(date);
			}
		} catch (Exception ex) {
			result = value;
		} finally {
			if (result == null) {
				result = value;
			}
		}
		return result;
	}

	private static String sourceFormat(String value) {
		String format = null;
		try {
			if (value.indexOf("/") == -1 && value.indexOf("-") == -1) {
				if (value.length() == 4) {
					format = "MMdd";
				} else if (value.length() == 6) {
					format = "HHmmss";
				} else if (value.length() == 8) {
					if (value.startsWith("20") || value.startsWith("19")) {
						format = "yyyyMMdd";
					} else {
						format = "MMddHHmm";
					}
				} else if (value.length() == 12) {
					format = "yyyyMMddHHmm";
				} else if (value.length() == 14) {
					format = "yyyyMMddHHmmss";
				} else if (value.length() > 14) {
					format = "yyyyMMddHHmmss";
					format = strFix(format, value.length(), false, "s");
				}
			} else if (value.indexOf("/") != -1) {
				if (value.indexOf(":") == -1) {
					format = "MM/dd/yyyy";
				} else {
					format = "MM/dd/yyyy HH:mm:ss";
				}
			} else if (value.indexOf("-") != -1) {
				if (value.indexOf(":") == -1) {
					format = "yyyy-MM-dd";
				} else {
					format = "yyyy-MM-dd HH:mm:ss";
				}
			}
		} catch (Exception ex) {
			format = "yyyyMMdd";
		} finally {
			if (format == null) {
				format = "yyyyMMdd";
			}
		}
		return format;
	}

	public static String secondsToMinutes(String sec) {
		String result = "0";
		double s = 0;
		double m = 0;
		if (isNumber(sec)) {
			s = Double.parseDouble(sec);
			m = s / 60.0;
			m = Math.floor(m + 0.5);
			result = String.valueOf((int) m);
		}

		return result;
	}

	public static String minutesToSeconds(String min) {
		String result = "0";
		double s = 0;
		double m = 0;
		if (isNumber(min)) {
			m = Double.parseDouble(min);
			s = m * 60.0;
			s = Math.floor(s + 0.5);
			result = String.valueOf((int) s);
		}

		return result;
	}

	public static String transToExtDateTimeFormat(String format) {
		String result = null;

		if (isNullOrEmpty(format)) {
			result = "Y-m-d H:i:s";
		} else {
			result = format.replace("yyyy", "Y").replace("dd", "d").replace("HH", "H").replace("mm", "i")
					.replace("ss", "s").replace("MM", "m");
		}
		return result;
	}

	public static String[] string2Array(String s) {
		return s.split("(?!^)");
	}

	public static Long parseString2Long(String radixText, int radix) {
		return Long.parseLong(radixText, radix);
	}

	public static String parseLong2String(long decimal, int radix) {
		return Long.toString(decimal, radix).toUpperCase();
	}

	public static int parseString2Num(String radixText, int radix) {
		return Integer.parseInt(radixText, radix);
	}

	public static String parseNum2String(int decimal, int radix) {
		return Integer.toString(decimal, radix).toUpperCase();
	}

	public static boolean mathRuleCheck(String rule) {
		boolean ok = false;
		Stack<Integer> tag = null;
		char[] c = null;
		char tmpchar;

		if (!isNullOrEmpty(rule)) {
			try {
				rule = rule.replaceAll(" ", "");
				rule = rule.replaceAll("\\+-", "\\+\\$").replaceAll("\\--", "\\-\\$")
						.replaceAll("\\*-", "\\*\\$").replaceAll("\\/-", "\\/\\$").replaceAll("\\+", "^")
						.replaceAll("\\-", "^").replaceAll("\\*", "^").replaceAll("\\/", "^");
				if (rule.indexOf("^") != -1) {
					if (rule.indexOf("(") == -1 && rule.indexOf(")") == -1) {
						ok = true;
					} else {
						tag = new Stack<Integer>();
						c = rule.toCharArray();
						for (int i = 0; i < c.length; i++) {
							tmpchar = c[i];
							if (tmpchar == '(') {
								tag.push(i);
							} else if (tmpchar == ')') {
								tag.pop();
							}
						}

						ok = tag.isEmpty();
					}
				}
			} catch (Exception ex) {
				ok = false;
			}
		}

		return ok;
	}

	public static String transRangValue(String value, String rule) {

		String result = null;
		if (isNullOrEmpty(value) || isNullOrEmpty(rule)) {
			result = value;
		} else {
			String[] rules = rule.replaceAll(" ", "").split(";");
			String[] rtmp = null;
			boolean match = false;
			char[] c = null;
			char tmpchar;
			ArrayList<int[]> point = null;
			point = new ArrayList<int[]>();
			int spoint = -1;
			int epoint = -1;
			String stmp = null;
			String etmp = null;
			String tag = null;
			try {
				if (rules != null && rules.length > 0) {
					for (int i = 0; i < rules.length; i++) {
						match = false;
						spoint = -1;
						epoint = -1;
						if (isNullOrEmpty(rules[i])) {
							continue;
						} else {
							rtmp = rules[i].split("#");
							if (rtmp == null || rtmp.length != 2) {
								continue;
							} else {
								if (!isNullOrEmpty(rtmp[1])) {
									c = rtmp[1].toCharArray();
									point = new ArrayList<int[]>();

									for (int j = 0; j < c.length; j++) {
										tmpchar = c[j];

										if (tmpchar == '=' || tmpchar == '<' || tmpchar == '>') {
											if (spoint == -1) {
												spoint = j;
											}
										} else {
											if (spoint != -1) {
												epoint = j;
											}
										}
										if (spoint != -1 && epoint != -1) {
											point.add(new int[] { spoint, epoint });
											spoint = -1;
											epoint = -1;
										}
									}

									if (point != null && point.size() > 0) {
										for (int j = 0; j < point.size(); j++) {
											if (j == 0) {
												stmp = rtmp[1].substring(0, point.get(j)[0]);
												tag = rtmp[1].substring(point.get(j)[0], point.get(j)[1]);
												spoint = point.get(j)[1];
												if (stmp.equals("$")) {
													stmp = value;
												}

												if (point.size() == 1) {
													etmp = rtmp[1].substring(point.get(j)[1]);
													if (etmp.equals("$")) {
														etmp = value;
													}
													match = compareValue(stmp, etmp, tag);
												} else {
													match = true;
												}

											} else if (j == point.size() - 1) {
												if (match) {
													if (spoint == -1) {
														match = false;
														break;
													} else {
														etmp = rtmp[1].substring(spoint, point.get(j)[0]);
														if (etmp.equals("$")) {
															etmp = value;
														}

														if (compareValue(stmp, etmp, tag)) {
															stmp = etmp;
															etmp = rtmp[1].substring(point.get(j)[1]);
															tag = rtmp[1].substring(point.get(j)[0],
																	point.get(j)[1]);
															if (etmp.equals("$")) {
																etmp = value;
															}
															match = compareValue(stmp, etmp, tag);
														} else {
															match = false;
														}
													}
												} else {
													break;
												}

											} else {
												if (match) {
													if (spoint == -1) {
														match = false;
														break;
													} else {
														etmp = rtmp[1].substring(spoint, point.get(j)[0]);
														if (etmp.equals("$")) {
															etmp = value;
														}
														match = compareValue(stmp, etmp, tag) && match;
														stmp = etmp;
														tag = rtmp[1].substring(point.get(j)[0],
																point.get(j)[1]);
														spoint = point.get(j)[1];
													}
												} else {
													break;
												}
											}
										}
									}

								}
							}
						}

						if (match) {
							result = rtmp[0];
							break;
						}
					}
				} else {
					result = value;
				}
			} catch (Exception ex) {
				result = value;
				ex.printStackTrace();
			}
			if (isNullOrEmpty(result)) {
				result = value;
			}
		}
		return result;
	}

	private static boolean compareValue(String stmp, String etmp, String tag) {
		boolean ok = false;
		double v1 = 0;
		double v2 = 0;

		try {
			if (isNumber(stmp) && isNumber(etmp) && !isNullOrEmpty(tag)) {
				if (tag.equals("=<")) {
					tag = "<=";
				} else if (tag.equals("=>")) {
					tag = ">=";
				}
				v1 = Double.parseDouble(stmp);
				v2 = Double.parseDouble(etmp);
				if (tag.equals("<")) {
					ok = v1 < v2;
				} else if (tag.equals(">")) {
					ok = v1 > v2;
				} else if (tag.equals("<=")) {
					ok = v1 <= v2;
				} else if (tag.equals(">=")) {
					ok = v1 >= v2;
				} else if (tag.equals("<>")) {
					ok = v1 != v2;
				} else {
					ok = false;
				}
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			ok = false;
			ex.printStackTrace();
		}
		return ok;
	}

	public static HashMap<String, String> jsonTranToStrMap(String json) {
		HashMap<String, String> result = null;
		try {
			if (!isNullOrEmpty(json)) {
				result = new ObjectMapper().readValue(json, HashMap.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == null) {
				result = new HashMap<String, String>();
			}
		}
		return result;
	}

	public static HashMap<String, Object> jsonTranToObjMap(String json) {
		HashMap<String, Object> result = null;
		try {
			if (!isNullOrEmpty(json)) {
				result = new ObjectMapper().readValue(json, HashMap.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == null) {
				result = new HashMap<String, Object>();
			}
		}
		return result;
	}

	public static Object jsonTranToObjMap(String json, Class<?> c) {
		Object result = null;
		try {
			if (!isNullOrEmpty(json)) {
				result = new ObjectMapper().readValue(json, c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == null) {
				result = new Object();
			}
		}
		return result;
	}

	public static String jsonTranFromArrayList(ArrayList<HashMap<String, String>> obj) {
		String result = null;
		try {
			result = new ObjectMapper().writeValueAsString(obj);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == null) {
				result = "";
			}
		}
		return result;
	}

	public static String transToDisplayVerFormat(String ver) {
		String result = null;
		String tmp = null;
		if (!DCIString.isNullOrEmpty(ver) && ver.length() == 12) {
			for (int i = 0; i < 4; i++) {
				tmp = ver.substring(i * 3, ((i + 1) * 3));
				if (i == 0) {
					result = String.valueOf(Integer.parseInt(tmp));
				} else {
					result += "." + String.valueOf(Integer.parseInt(tmp));
				}
			}
		}

		if (result == null) {
			result = ver;
		}
		return result;
	}

	public static String transToLongVerFormat(String ver) {
		String result = null;
		String[] vercodes = null;

		vercodes = ver.split("\\.");

		for (int i = 0; i < vercodes.length; i++) {
			if (i == 0) {
				result = strFix(vercodes[i], 3, true, "0");
			} else {
				result += strFix(vercodes[i], 3, true, "0");
			}
		}
		return result;
	}
}
