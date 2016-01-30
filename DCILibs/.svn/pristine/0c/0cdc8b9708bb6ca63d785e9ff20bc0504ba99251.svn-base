package com.dci.jweb.DCIEnums.Server;

public enum BlockPattern {
	P01(1), P02(2), P03(3), P04(4);

	private int value;

	private BlockPattern(int value) {
		this.value = value;
	}

	public static BlockPattern valueOf(int value) {
		switch (value) {
		case 1:
			return P01;
		case 2:
			return P02;
		case 3:
			return P03;
		case 4:
			return P04;
		default:
			return null;
		}
	}

	public int value() {
		return this.value;
	}

}
