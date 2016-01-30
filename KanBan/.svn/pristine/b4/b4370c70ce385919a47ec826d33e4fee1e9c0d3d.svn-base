package com.dsc.dci.jweb.apenums;

public enum RangeDisplayType {
	GRID, LIGHT, PCT;

	public static RangeDisplayType valueOf(ColumnType value) {
		RangeDisplayType result = RangeDisplayType.GRID;

		if (value == ColumnType.PROGRESS) {
			result = RangeDisplayType.PCT;
		} else if (value == ColumnType.LIGHT) {
			result = RangeDisplayType.LIGHT;
		}
		
		return result;
	}
}
