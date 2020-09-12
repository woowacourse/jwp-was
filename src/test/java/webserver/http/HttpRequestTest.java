// package webserver.http;
//
// import static org.assertj.core.api.Assertions.*;
// import static org.junit.jupiter.api.Assertions.*;
//
// import java.io.IOException;
// import java.net.URISyntaxException;
// import java.util.HashMap;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
//
// import utils.FileIoUtils;
// import webserver.HttpMethod;
// import webserver.process.get.GetUrlProcessor;
//
// class HttpRequestTest {
//
// 	@DisplayName("Http 요청에 알맞게 처리한다.")
// 	@Test
// 	void process() throws IOException, URISyntaxException {
// 		HashMap<String, String> headers = new HashMap<>();
// 		headers.put(HeaderName.Method.name(), HttpMethod.GET.name());
// 		headers.put(HeaderName.RequestUrl.name(), "/index.html");
// 		HttpHeaders httpHeaders = new HttpHeaders(headers);
// 		HttpBody httpBody = new HttpBody("");
// 		HttpRequest httpRequest = new HttpRequest(httpHeaders, httpBody);
//
// 		byte[] actual = httpRequest.process();
//
// 		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/index.html");
//
// 		assertThat(actual).isEqualTo(expected);
// 	}
// }