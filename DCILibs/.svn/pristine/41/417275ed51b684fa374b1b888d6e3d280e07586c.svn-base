package com.dci.jweb.PublicLib;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Stack;

public class DCINumber {

	/**
	 * @param args
	 */

	public static String calcNumber_ori(ResultSet data, String calcRule, HashMap<String, String> cols) {
		String result = null;
		String[] mrules = null;
		String[] rulecol = null;
		double mathvalue = 0;
		boolean dataseted = false;

		if (!DCIString.isNullOrEmpty(calcRule) && data != null) {
			try {
				mrules = calcRule.split("\\$");

				for (int i = 0; i < mrules.length; i++) {
					if (i == 0) {
						if (cols.containsKey(mrules[i].trim())) {
							if (data.getObject(mrules[i].trim()) != null
									&& DCIString.isNumber(data.getString(mrules[i].trim()).trim())) {
								mathvalue = Double.parseDouble(data.getString(mrules[i].trim()).trim());
							}
						} else if (mrules[i] != null && mrules[i].trim().length() > 0
								&& DCIString.isNumber(mrules[i].trim())) {
							mathvalue = Double.parseDouble(mrules[i].trim());
						} else {
							result = "";
							dataseted = true;
							break;
						}

					} else {
						rulecol = mrules[i].split("#");
						if (rulecol != null && rulecol.length >= 2) {
							if (cols.containsKey(rulecol[1].trim())) {
								if (data.getObject(rulecol[1].trim()) != null) {
									if (DCIString.isNumber(data.getString(rulecol[1].trim()).trim())) {
										if (rulecol[0].equals("+")) {
											mathvalue = mathvalue
													+ Double.parseDouble(data.getString(rulecol[1].trim())
															.trim());
										} else if (rulecol[0].equals("-")) {
											mathvalue = mathvalue
													- Double.parseDouble(data.getString(rulecol[1].trim())
															.trim());
										} else if (rulecol[0].equals("*")) {
											mathvalue = mathvalue
													* Double.parseDouble(data.getString(rulecol[1].trim())
															.trim());
										} else if (rulecol[0].equals("/")) {
											if (Double.parseDouble(data.getString(rulecol[1]).trim()) != 0) {
												mathvalue = mathvalue
														/ Double.parseDouble(data
																.getString(rulecol[1].trim()).trim());
											} else {
												result = "";
												dataseted = true;
												break;
											}
										} else {
											result = "";
											dataseted = true;
											break;
										}
									}
								}
							} else if (rulecol[1] != null && rulecol[1].trim().length() > 0
									&& DCIString.isNumber(rulecol[1].trim())) {
								if (rulecol[0].equals("+")) {
									mathvalue = mathvalue + Double.parseDouble(rulecol[1].trim());
								} else if (rulecol[0].equals("-")) {
									mathvalue = mathvalue - Double.parseDouble(rulecol[1].trim());
								} else if (rulecol[0].equals("*")) {
									mathvalue = mathvalue * +Double.parseDouble(rulecol[1].trim());
								} else if (rulecol[0].equals("/")) {
									if (Double.parseDouble(rulecol[1].trim()) != 0) {
										mathvalue = mathvalue / Double.parseDouble(rulecol[1].trim());
									} else {
										result = "";
										dataseted = true;
										break;
									}
								} else {
									result = "";
									dataseted = true;
									break;
								}
							} else {
								result = "";
								dataseted = true;
								break;
							}
						} else {
							result = "";
							dataseted = true;
							break;
						}
					}
				}
			} catch (Exception ex) {
				result = "";
				dataseted = true;
			}
			if (!dataseted) {
				result = String.valueOf(mathvalue);
			}

		}
		return result;
	}

	public static String calcNumber_ori(HashMap<String, Object> data, String calcRule) {
		String result = null;
		String[] mrules = null;
		String[] rulecol = null;
		double mathvalue = 0;
		boolean dataseted = false;

		if (!DCIString.isNullOrEmpty(calcRule) && data != null) {
			try {
				mrules = calcRule.split("\\$");

				for (int i = 0; i < mrules.length; i++) {
					if (i == 0) {
						if (data.containsKey(mrules[i].trim())) {
							if (data.get(mrules[i].trim()) != null
									&& DCIString.isNumber(data.get(mrules[i].trim()).toString().trim())) {
								mathvalue = Double.parseDouble(data.get(mrules[i].trim()).toString().trim());
							}
						} else if (mrules[i] != null && mrules[i].trim().length() > 0
								&& DCIString.isNumber(mrules[i].trim())) {
							mathvalue = Double.parseDouble(mrules[i].trim());
						} else {
							result = "";
							dataseted = true;
							break;
						}
					} else {
						rulecol = mrules[i].split("#");
						if (rulecol != null && rulecol.length >= 2) {
							if (data.containsKey(rulecol[1].trim())) {
								if (data.get(rulecol[1].trim()) != null
										&& DCIString.isNumber(data.get(rulecol[1].trim()).toString().trim())) {
									if (rulecol[0].equals("+")) {
										mathvalue = mathvalue
												+ Double.parseDouble(data.get(rulecol[1].trim()).toString()
														.trim());
									} else if (rulecol[0].equals("-")) {
										mathvalue = mathvalue
												- Double.parseDouble(data.get(rulecol[1].trim()).toString()
														.trim());
									} else if (rulecol[0].equals("*")) {
										mathvalue = mathvalue
												* Double.parseDouble(data.get(rulecol[1].trim()).toString()
														.trim());
									} else if (rulecol[0].equals("/")) {
										if (Double.parseDouble(data.get(rulecol[1].trim()).toString()) != 0) {
											mathvalue = mathvalue
													/ Double.parseDouble(data.get(rulecol[1].trim())
															.toString().trim());
										} else {
											result = "";
											dataseted = true;
											break;
										}
									} else {
										result = "";
										dataseted = true;
										break;
									}
								}
							} else if (rulecol[1] != null && rulecol[1].trim().length() > 0
									&& DCIString.isNumber(rulecol[1].trim())) {

								if (rulecol[0].equals("+")) {
									mathvalue = mathvalue + Double.parseDouble(rulecol[1].trim());
								} else if (rulecol[0].equals("-")) {
									mathvalue = mathvalue - Double.parseDouble(rulecol[1].trim());
								} else if (rulecol[0].equals("*")) {
									mathvalue = mathvalue * +Double.parseDouble(rulecol[1].trim());
								} else if (rulecol[0].equals("/")) {
									if (Double.parseDouble(rulecol[1].trim()) != 0) {
										mathvalue = mathvalue / Double.parseDouble(rulecol[1].trim());
									} else {
										result = "";
										dataseted = true;
										break;
									}
								} else {
									result = "";
									dataseted = true;
									break;
								}
							} else {
								result = "";
								dataseted = true;
								break;
							}
						} else {
							result = "";
							dataseted = true;
							break;
						}
					}
				}
			} catch (Exception ex) {
				result = "";
				dataseted = true;
			}
			if (!dataseted) {
				result = String.valueOf(mathvalue);
			}

		}
		return result;
	}

	public static String calcNumber(ResultSet data, String rule) {
		String result = null;
		Stack<Integer> tag = null;
		ArrayList<int[]> point = null;
		char[] c = null;
		int[] tmp = null;
		char tmpchar;
		// String tmprule = null;

		if (rule == null || data == null) {
			result = "";
		} else {
			if (DCIString.mathRuleCheck(rule)) {
				try {
					rule = rule.replace(" ", "");
					if (rule.indexOf("(") == -1) {
						result = calculate(data, rule);
					} else {
						tag = new Stack<Integer>();
						c = rule.toCharArray();
						point = new ArrayList<int[]>();
						for (int i = 0; i < c.length; i++) {
							tmpchar = c[i];
							if (tmpchar == '(') {
								tag.push(i);
							} else if (tmpchar == ')') {
								point.add(new int[] { tag.pop(), i });
								break;
							}
						}
						// tmprule = rule;
						for (int i = 0; i < point.size(); i++) {
							tmp = point.get(i);
							// System.out.println(rule.substring(tmp[0] + 1,
							// tmp[1]));
							result = calculate(data, rule.substring(tmp[0] + 1, tmp[1]));
							if (DCIString.isNumber(result)) {
								rule = rule.substring(0, tmp[0]) + result + rule.substring(tmp[1] + 1);
								// System.out.println(rule);
								if (rule.indexOf("(") == -1) {
									result = calculate(data, rule);
								} else {
									result = calcNumber(data, rule);
								}
							}
						}
					}
				} catch (Exception ex) {
					result = "";
					ex.printStackTrace();
				}
			} else {
				if (DCIString.isNumber(rule)) {
					result = rule;
				} else {
					result = "";
				}
			}
		}

		return result;
	}

	public static String calcNumber(HashMap<String, Object> data, String rule) {
		String result = null;
		Stack<Integer> tag = null;
		ArrayList<int[]> point = null;
		char[] c = null;
		int[] tmp = null;
		char tmpchar;
		// String tmprule = null;

		if (rule == null || data == null) {
			result = "";
		} else {
			if (DCIString.mathRuleCheck(rule)) {
				try {
					rule = rule.replace(" ", "");
					if (rule.indexOf("(") == -1) {
						result = calculate(data, rule);
					} else {
						tag = new Stack<Integer>();
						c = rule.toCharArray();
						point = new ArrayList<int[]>();
						for (int i = 0; i < c.length; i++) {
							tmpchar = c[i];
							if (tmpchar == '(') {
								tag.push(i);
							} else if (tmpchar == ')') {
								point.add(new int[] { tag.pop(), i });
								break;
							}
						}
						// tmprule = rule;
						for (int i = 0; i < point.size(); i++) {
							tmp = point.get(i);
							// System.out.println(rule.substring(tmp[0] + 1,
							// tmp[1]));
							result = calculate(data, rule.substring(tmp[0] + 1, tmp[1]));
							if (DCIString.isNumber(result)) {
								rule = rule.substring(0, tmp[0]) + result + rule.substring(tmp[1] + 1);
								// System.out.println(rule);
								if (rule.indexOf("(") == -1) {
									result = calculate(data, rule);
								} else {
									result = calcNumber(data, rule);
								}
							}
						}
					}
				} catch (Exception ex) {
					result = "";
					ex.printStackTrace();
				}
			} else {
				if (DCIString.isNumber(rule)) {
					result = rule;
				} else {
					result = "";
				}
			}
		}

		return result;
	}

	private static String calculate(ResultSet data, String calcRule) {
		String result = null;
		String tmprule = null;
		String tmpvalue = null;
		String op = null;
		String[] nums = null;
		double value = 0;
		int len = 0;

		try {
			if (DCIString.isNullOrEmpty(calcRule)) {
				result = "";
			} else {
				calcRule = calcRule.replaceAll(" ", "");
				if (calcRule.length() >= 1 && calcRule.substring(0, 1).equals("-")) {
					calcRule = "$" + calcRule.substring(1);
				}
				tmprule = calcRule.replaceAll("\\+-", "\\+\\$").replaceAll("\\--", "\\-\\$")
						.replaceAll("\\*-", "\\*\\$").replaceAll("\\/-", "\\/\\$").replaceAll("\\+", "^")
						.replaceAll("\\-", "^");
				nums = tmprule.split("\\^");

				for (int i = 0; i < nums.length; i++) {
					nums[i] = nums[i].replaceAll("\\$", "-");
					if (i == 0) {
						if (nums[i].indexOf("*") != -1 || nums[i].indexOf("/") != -1) {
							tmpvalue = calculateMCL(data, nums[i]);
							if (DCIString.isNullOrEmpty(tmpvalue)) {
								throw new Exception();
							} else {
								value = Double.parseDouble(tmpvalue);
							}
						} else {
							if (DCIString.isNumber(nums[i])) {
								value = Double.parseDouble(nums[i]);
							} else {
								value = Double.parseDouble(data.getString(nums[i]).trim());
							}
						}
						len = nums[i].length();
					} else {
						op = calcRule.substring(len, len + 1);
						if (nums[i].indexOf("*") != -1 || nums[i].indexOf("/") != -1) {
							tmpvalue = calculateMCL(data, nums[i]);
							if (DCIString.isNullOrEmpty(tmpvalue)) {
								throw new Exception("");
							} else {
								tmpvalue = calculate(String.valueOf(value), tmpvalue, op);
							}

						} else {
							if (DCIString.isNumber(nums[i])) {
								tmpvalue = calculate(String.valueOf(value), nums[i], op);
							} else {
								tmpvalue = calculate(String.valueOf(value), data.getString(nums[i]).trim(),
										op);
							}
						}

						if (DCIString.isNullOrEmpty(tmpvalue)) {
							throw new Exception();
						} else {
							value = Double.parseDouble(tmpvalue);
						}
						len += nums[i].length() + 1;
					}
				}
				result = String.valueOf(value);
			}
		} catch (Exception ex) {
			result = "";
			ex.printStackTrace();
		}

		return result;
	}

	private static String calculateMCL(ResultSet data, String calcRule) {
		String result = null;
		String tmpresult = null;
		String tmprule = null;
		String[] nums = null;
		double value = 0;
		int len = 0;
		String op = null;

		if (DCIString.isNullOrEmpty(calcRule) || data == null) {
			result = "";
		} else {
			try {
				calcRule = calcRule.replaceAll(" ", "");
				tmprule = calcRule.replaceAll("\\*", "^").replaceAll("\\/", "^");
				nums = tmprule.split("\\^");
				value = 0;
				for (int j = 0; j < nums.length; j++) {
					if (j == 0) {
						if (DCIString.isNumber(nums[j])) {
							value = Double.parseDouble(nums[j]);
						} else {
							value = Double.parseDouble(data.getString(nums[j]).trim());
						}
						len = nums[j].length();
					} else {
						op = calcRule.substring(len, len + 1);
						if (DCIString.isNumber(nums[j])) {
							tmpresult = calculate(String.valueOf(value), nums[j], op);
						} else {
							tmpresult = calculate(String.valueOf(value), data.getString(nums[j]).trim(), op);
						}
						if (DCIString.isNullOrEmpty(tmpresult)) {
							throw new Exception();
						} else {
							value = Double.parseDouble(tmpresult);
						}

						len += nums[j].length() + 1;
					}
				}
				result = String.valueOf(value);
			} catch (Exception ex) {
				result = "";
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static String calculate(HashMap<String, Object> data, String calcRule) {
		String result = null;
		String tmprule = null;
		String tmpvalue = null;
		String op = null;
		String[] nums = null;
		double value = 0;
		int len = 0;

		try {
			if (DCIString.isNullOrEmpty(calcRule)) {
				result = "";
			} else {
				calcRule = calcRule.replaceAll(" ", "");
				if (calcRule.length() >= 1 && calcRule.substring(0, 1).equals("-")) {
					calcRule = "$" + calcRule.substring(1);
				}
				tmprule = calcRule.replaceAll("\\+-", "\\+\\$").replaceAll("\\--", "\\-\\$")
						.replaceAll("\\*-", "\\*\\$").replaceAll("\\/-", "\\/\\$").replaceAll("\\+", "^")
						.replaceAll("\\-", "^");
				nums = tmprule.split("\\^");

				for (int i = 0; i < nums.length; i++) {
					nums[i] = nums[i].replaceAll("\\$", "-");
					if (i == 0) {
						if (nums[i].indexOf("*") != -1 || nums[i].indexOf("/") != -1) {
							tmpvalue = calculateMCL(data, nums[i]);
							if (DCIString.isNullOrEmpty(tmpvalue)) {
								throw new Exception();
							} else {
								value = Double.parseDouble(tmpvalue);
							}
						} else {
							if (DCIString.isNumber(nums[i])) {
								value = Double.parseDouble(nums[i]);
							} else {
								value = Double.parseDouble(data.get(nums[i]).toString().trim());
							}
						}
						len = nums[i].length();
					} else {
						op = calcRule.substring(len, len + 1);
						if (nums[i].indexOf("*") != -1 || nums[i].indexOf("/") != -1) {
							tmpvalue = calculateMCL(data, nums[i]);
							if (DCIString.isNullOrEmpty(tmpvalue)) {
								throw new Exception("");
							} else {
								tmpvalue = calculate(String.valueOf(value), tmpvalue, op);
							}

						} else {
							if (DCIString.isNumber(nums[i])) {
								tmpvalue = calculate(String.valueOf(value), nums[i], op);
							} else {
								tmpvalue = calculate(String.valueOf(value), data.get(nums[i]).toString(), op);
							}
						}

						if (DCIString.isNullOrEmpty(tmpvalue)) {
							throw new Exception();
						} else {
							value = Double.parseDouble(tmpvalue);
						}
						len += nums[i].length() + 1;
					}
				}
				result = String.valueOf(value);
			}
		} catch (Exception ex) {
			result = "";
			ex.printStackTrace();
		}

		return result;
	}

	private static String calculateMCL(HashMap<String, Object> data, String calcRule) {
		String result = null;
		String tmpresult = null;
		String tmprule = null;
		String[] nums = null;
		double value = 0;
		int len = 0;
		String op = null;

		if (DCIString.isNullOrEmpty(calcRule) || data == null) {
			result = "";
		} else {
			try {
				calcRule = calcRule.replaceAll(" ", "");
				tmprule = calcRule.replaceAll("\\*", "^").replaceAll("\\/", "^");
				nums = tmprule.split("\\^");
				value = 0;
				for (int j = 0; j < nums.length; j++) {
					if (j == 0) {
						if (DCIString.isNumber(nums[j])) {
							value = Double.parseDouble(nums[j]);
						} else {
							value = Double.parseDouble(data.get(nums[j]).toString().trim());
						}
						len = nums[j].length();
					} else {
						op = calcRule.substring(len, len + 1);
						if (DCIString.isNumber(nums[j])) {
							tmpresult = calculate(String.valueOf(value), nums[j], op);
						} else {
							tmpresult = calculate(String.valueOf(value), data.get(nums[j]).toString(), op);
						}
						if (DCIString.isNullOrEmpty(tmpresult)) {
							throw new Exception();
						} else {
							value = Double.parseDouble(tmpresult);
						}

						len += nums[j].length() + 1;
					}
				}
				result = String.valueOf(value);
			} catch (Exception ex) {
				result = "";
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static String calculate(String num1, String num2, String op) {
		String result = null;

		if (DCIString.isNullOrEmpty(num1) || DCIString.isNullOrEmpty(num1) || DCIString.isNullOrEmpty(num1)) {
			result = "";
		} else {
			try {
				num1 = num1.replaceAll(" ", "");
				num2 = num2.replaceAll(" ", "");
				if (op.equals("+")) {
					result = String.valueOf(Double.parseDouble(num1) + Double.parseDouble(num2));
				} else if (op.equals("-")) {
					result = String.valueOf(Double.parseDouble(num1) - Double.parseDouble(num2));
				} else if (op.equals("*")) {
					result = String.valueOf(Double.parseDouble(num1) * Double.parseDouble(num2));
				} else if (op.equals("/")) {
					if (Double.parseDouble(num2) != 0) {
						result = String.valueOf(Double.parseDouble(num1) / Double.parseDouble(num2));
					} else {
						result = "";
					}
				} else {
					result = "";
				}

			} catch (Exception ex) {
				result = "";
				ex.printStackTrace();
			}
		}
		return result;
	}
}
