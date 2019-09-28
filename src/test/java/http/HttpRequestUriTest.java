package http;

import http.exception.EmptyUriException;
import http.request.HttpRequestUri;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestUriTest {

    @Test
    void 정적_파일_uri_정상_생성() {
        assertDoesNotThrow(() -> new HttpRequestUri("/index.html"));
    }

    @Test
    void 비어있는_uri_생성_오류() {
        assertThrows(EmptyUriException.class, () -> new HttpRequestUri(""));
    }

    @Test
    void NULL_uri_생성_오류() {
        assertThrows(EmptyUriException.class, () -> new HttpRequestUri(null));
    }

    @Test
    void 쿼리스트링을_포함한_uri에서_쿼리스트링_추가() {
        String uriIncludedQueryString = "/user/create?id=1&name=park";
        HttpRequestUri httpRequestUri = new HttpRequestUri(uriIncludedQueryString);
        assertTrue(httpRequestUri.addQueryString(new QueryString()));
    }

    @Test
    void 쿼리스트링을_포함하지않은_uri에서_쿼리스트링_추가() {
        String uriIncludedQueryString = "/user/create";
        HttpRequestUri httpRequestUri = new HttpRequestUri(uriIncludedQueryString);
        assertFalse(httpRequestUri.addQueryString(new QueryString()));
    }
}
