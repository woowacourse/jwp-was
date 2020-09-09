package utils;

public class StringSplitUtils {
	public static String splitAndFetch(String target, String delimiter, int fetchingIndex) {
		return target.split(delimiter)[fetchingIndex];
	}
}
