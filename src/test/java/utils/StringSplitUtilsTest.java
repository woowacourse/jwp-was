package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringSplitUtilsTest {
	@DisplayName("주어진 String을 delimiter에 맞춰 split한 후 해당되는 index의 문자열을 가져온다.")
	@Test
	void splitAndFetch() {
		String actual = "GET /index.html HTTP/1.1";
		String delimiter = " ";
		int index = 1;
		String expected = "/index.html";

		assertEquals(StringSplitUtils.splitAndFetch(actual, delimiter, index), expected);
	}
}