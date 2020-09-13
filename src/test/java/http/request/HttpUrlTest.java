package http.request;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class HttpUrlTest {

    private static final String URL_WITHOUT_PARAM = "/user/create";
    private static final String URL = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    private static final String PATH = "/user/create";
    private static final Map<String, String> PARAMS = new HashMap<String, String>() {{
        put("userId", "javajigi");
        put("password", "password");
        put("name", "박재성");
        put("email", "javajigi@slipp.net");
    }};

    @Test
    public void construct() {
        HttpUrl httpUrl = HttpUrl.from(URL);
        assertThat(httpUrl.getPath()).isEqualTo(PATH);
        assertThat(httpUrl.getParams()).isEqualTo(PARAMS);
    }

    @Test
    public void constructWithoutParam() {
        HttpUrl httpUrl = HttpUrl.from(URL_WITHOUT_PARAM);
        assertThat(httpUrl.getPath()).isEqualTo(PATH);
        assertThat(httpUrl.getParams()).isEmpty();
    }
}
