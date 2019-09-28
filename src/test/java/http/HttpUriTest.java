package http;

import http.exception.EmptyUriException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpUriTest {

    @Test
    void 정적_파일_uri_정상_생성() {
        assertDoesNotThrow(() -> new HttpUri("/index.html"));
    }

    @Test
    void 비어있는_uri_생성_오류() {
        assertThrows(EmptyUriException.class, () -> new HttpUri(""));
    }

    @Test
    void NULL_uri_생성_오류() {
        assertThrows(EmptyUriException.class, () -> new HttpUri(null));
    }

    @Test
    void 쿼리스트링을_포함한_uri에서_쿼리스트링_추가() {
        String uriIncludedQueryString = "/user/create?id=1&name=park";
        HttpUri httpUri = new HttpUri(uriIncludedQueryString);
        assertTrue(httpUri.addQueryString(new QueryString()));
    }

    @Test
    void 쿼리스트링을_포함하지않은_uri에서_쿼리스트링_추가() {
        String uriIncludedQueryString = "/user/create";
        HttpUri httpUri = new HttpUri(uriIncludedQueryString);
        assertFalse(httpUri.addQueryString(new QueryString()));
    }
}
