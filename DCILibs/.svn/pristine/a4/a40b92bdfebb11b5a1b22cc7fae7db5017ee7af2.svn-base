package com.dci.jweb.PublicLib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

public class DCIExcel {
	public void closeWorkBook(HSSFWorkbook workbook) {
		if (workbook != null) {
			try {
				workbook.close();
			} catch (IOException e) {

			} finally {
				workbook = null;
			}
		}
	}

	public CellStyle buildDefHeaderStyle(HSSFWorkbook workbook) {
		CellStyle style = null;
		Font font = null;
		if (workbook != null) {
			style = workbook.createCellStyle();
			font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);// 顏色
			font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗體
			style.setFont(font);
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// 填滿顏色
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平置中
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直置中
			style.setBorderBottom((short) 1);
			style.setBorderTop((short) 1);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setWrapText(true);// 自動換行
		}
		return style;
	}

	public void setHeaderCells(HSSFSheet sheet, HSSFRow row, HSSFCell cell, CellStyle style,
			ArrayList<HashMap<String, String>> cols) {
		double tmp = 0;
		for (int j = 0; j < cols.size(); j++) {
			if (!Boolean.parseBoolean(cols.get(j).get("hidden"))) {
				tmp = Double.parseDouble(cols.get(j).get("width")) * 36.5;

				cell = row.createCell(j);
				cell.setCellValue(cols.get(j).get("text"));
				sheet.setColumnWidth(j, Integer.parseInt(String.valueOf(Math.round(tmp))));

				cell.setCellStyle(style);
			}
		}
	}

	public void setCellLightStyle(HSSFWorkbook workbook, HSSFCell cell, String value) {
		CellStyle style = null;
		Font font = null;
		if (workbook != null && cell != null) {
			if (DCIString.isNullOrEmpty(value)) {
				cell.setCellValue("");
			} else {
				cell.setCellValue("●");
				style = workbook.createCellStyle();
				font = workbook.createFont();
				if (value.equals("R")) {
					font.setColor(HSSFColor.RED.index);
				} else if (value.equals("Y")) {
					font.setColor(HSSFColor.YELLOW.index);
				} else {
					cell.setCellValue("");
					font.setColor(HSSFColor.BLACK.index);
				}
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				style.setFont(font);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cell.setCellStyle(style);
			}
		}
	}

	public byte[] transToDownloadObj(HSSFWorkbook workbook) {
		byte[] result = null;
		ByteArrayOutputStream baos = null;

		try {
			if (workbook != null) {
				baos = new ByteArrayOutputStream();
				workbook.write(baos);

				result = baos.toByteArray();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			baos = null;
		}
		return result;
	}
}
