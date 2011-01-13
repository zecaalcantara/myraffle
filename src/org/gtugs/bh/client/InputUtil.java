package org.gtugs.bh.client;

import java.util.Arrays;
import java.util.TreeSet;

public class InputUtil {

	public static String[] transformTextToArray(String text) {
		if (text!=null) {
			text=text.trim();
			if (!text.isEmpty()) {
				return text.split("\n");
			}
		}
		return new String[0];
	}
	
	public static TreeSet<String> transformArrayToSet(String[] items) {
		if (items==null) {
			return null;
		}
		return new TreeSet<String>(Arrays.asList(items));
	}
	
	public static TreeSet<String> transformRangeToSet(Integer from, Integer to) {
		if (from==null || to==null || from>=to) {
			return null;
		}
		TreeSet<String> result=new TreeSet<String>();
		for (int i=from; i<=to; i++) {
			result.add(String.valueOf(i));
		}
		return result;
	}
	
	public static boolean validateRange(Integer from, Integer to) {
		return (from!=null && to!=null && from<to);
	}
	
	public static Integer transformTextToInteger(String text) {
		if (text!=null && !text.trim().isEmpty()) {
			try {
				return Integer.parseInt(text);
			} catch (NumberFormatException ex) {
			}
		}
		return null;
	}
	
	public static String transformArrayToString(String[] arr) {
		if (arr==null || arr.length==0) {
			return "";
		}
		StringBuffer sb=new StringBuffer();
		for (String a: arr) {
			sb.append(a).append("\n"); // TODO: change to generic line separator
		}
		return sb.toString();
	}

	public static String transformIntegerToString(Integer i) {
		if (i==null) {
			return "";
		}
		return String.valueOf(i);
	}

}
