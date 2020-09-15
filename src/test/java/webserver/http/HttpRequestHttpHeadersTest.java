package webserver.http;

class HttpRequestHttpHeadersTest {

	// @DisplayName("url 정보 확인 - 참")
	// @Test
	// void isSameUrl() {
	// 	HashMap<String, String> headers = new HashMap<>();
	// 	headers.put(HttpRequestHeaderName.REQUEST_TARGET.name(), "/url");
	// 	HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
	//
	// 	assertTrue(httpRequestHttpHeaders.isSameUrl("/url"));
	// }
	//
	// @DisplayName("url 정보 확인 - 거짓")
	// @Test
	// void isNotSameUrl() {
	// 	HashMap<String, String> headers = new HashMap<>();
	// 	headers.put(HttpRequestHeaderName.REQUEST_TARGET.name(), "/url");
	// 	HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
	//
	// 	assertFalse(httpRequestHttpHeaders.isSameUrl("/anotherurl"));
	// }
	//
	// @DisplayName("url에 포함되는지 확인 - 참")
	// @Test
	// void containsInUrl() {
	// 	HashMap<String, String> headers = new HashMap<>();
	// 	headers.put(HttpRequestHeaderName.REQUEST_TARGET.name(), "/contains/url");
	// 	HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
	//
	// 	assertTrue(httpRequestHttpHeaders.containsInUrl("/contains"));
	// }
	//
	// @DisplayName("url에 포함되는지 확인 - 거짓")
	// @Test
	// void containsNotInUrl() {
	// 	HashMap<String, String> headers = new HashMap<>();
	// 	headers.put(HttpRequestHeaderName.REQUEST_TARGET.name(), "/contains/url");
	// 	HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
	//
	// 	assertFalse(httpRequestHttpHeaders.containsInUrl("/another/url"));
	// }
	//
	// @DisplayName("POST가 아닌 method type인 경우 바디를 포함하지 않는다.")
	// @Test
	// void hasNotBody() {
	// 	HashMap<String, String> headers = new HashMap<>();
	// 	headers.put(HttpRequestHeaderName.HTTP_METHOD.name(), "GET");
	// 	HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
	//
	// 	assertTrue(httpRequestHttpHeaders.hasNotBody());
	// }
	//
	// @DisplayName("POST method type인 경우 바디를 포함")
	// @Test
	// void hasBody() {
	// 	HashMap<String, String> headers = new HashMap<>();
	// 	headers.put(HttpRequestHeaderName.HTTP_METHOD.name(), "POST");
	// 	HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
	//
	// 	assertFalse(httpRequestHttpHeaders.hasNotBody());
	// }
}