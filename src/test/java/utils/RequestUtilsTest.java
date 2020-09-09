package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class RequestUtilsTest {

    private static final String REQUEST =
        "GET /user/create?userId=javajigi&password=password&name=포비&email=javajigi@slipp.net HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
    private static final String WHOLE_URL = "/user/create?userId=javajigi&password=password&name=포비&email=javajigi@slipp.net";
    private static final String PATH = "/user/create";
    private static final Map<String, String> PARAMS = new HashMap<String, String>() {{
        put("userId", "javajigi");
        put("password", "password");
        put("name", "포비");
        put("email", "javajigi@slipp.net");
    }};

    @Test
    public void extractWholeUrl() {
        String wholeUrl = RequestUtils.extractWholeUrl(REQUEST);
        assertThat(wholeUrl).isEqualTo(WHOLE_URL);
    }

    @Test
    public void extractPath() {
        String path = RequestUtils.extractPath(WHOLE_URL);
        assertThat(path).isEqualTo(PATH);
    }

    @Test
    public void extractParams() {
        Map<String, String> params = RequestUtils.extractParams(WHOLE_URL);
        assertThat(params.equals(PARAMS));
    }
}