package com.kodikasgroup.utils;

import java.util.regex.Pattern;

public class Utils {
	private Utils() {}

	public static boolean isValidFiscalCode(String text) {
		String pattern = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$";
		return Pattern.matches(pattern, text);
	}
}
