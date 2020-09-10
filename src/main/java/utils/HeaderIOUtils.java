package utils;

import webserver.HttpMainHeader;
import webserver.HttpMethod;

public class HeaderIOUtils {

	public static HttpMainHeader parseMainHeader(String mainHeadersLine) {
		String[] mainHeaders = mainHeadersLine.split(" ");
		return new HttpMainHeader(HttpMethod.of(mainHeaders[0]), mainHeaders[1]);
	}
}
