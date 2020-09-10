package utils;

public class HeaderIOUtils {

	public static String parseUrl(String mainHeadersLine) {
		String[] mainHeaders = mainHeadersLine.split(" ");
		return mainHeaders[1];
	}
}
