package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsTest {
    private static final String ATTR_1 = "attr1";
    private static final String VAL_1 = "val1";
    private static final String ATTR_2 = "attr2";
    private static final String VAL_2 = "val2";

    private String requestLine;

    @BeforeEach
    void setUp() {
        requestLine = "GET /url/path?" + ATTR_1 + "=" + VAL_1
                + "&" + ATTR_2 + "=" + VAL_2 + " Http/1.1";
    }

    @Test
    @DisplayName("RequestLine을 parsing할 수 있으면 true를 리턴한다.")
    void checkParsable() {
        assertThat(QueryParams.canParse(requestLine)).isTrue();
    }

    @Test
    @DisplayName("RequestLine을 parsing할 수 없으면 false를 리턴한다.")
    void checkNotParsable() {
        String requestLine = "GET /url/path Http/1.1";

        assertThat(QueryParams.canParse(requestLine)).isFalse();
    }

    @Test
    @DisplayName("RequestLine에서 QueryParams를 parsing해낸다.")
    void parseRequestLine() throws UnsupportedEncodingException {
        QueryParams queryParams = QueryParams.parseRequestLine(requestLine);

        assertThat(queryParams.getParam(ATTR_1)).isEqualTo(VAL_1);
        assertThat(queryParams.getParam(ATTR_2)).isEqualTo(VAL_2);
    }

    @Test
    @DisplayName("body에서 QueryParams를 parsing 해낸다.")
    void parseBody() {
        String body = ATTR_1 + "=" + VAL_1 + "&" + ATTR_2 + "=" + VAL_2;

        QueryParams queryParams = QueryParams.parseBody(body);

        assertThat(queryParams.getParam(ATTR_1)).isEqualTo(VAL_1);
        assertThat(queryParams.getParam(ATTR_2)).isEqualTo(VAL_2);
    }

//    @Test
//    void 생성() {
//        QueryParams queryParams = QueryParams.of(requestLine);
//
//        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
//        assertThat(queryParams.getParam("password")).isEqualTo("password");
//        assertThat(queryParams.getParam("name")).isEqualTo("woo");
//    }
}