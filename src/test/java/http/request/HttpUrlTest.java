package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import stringprocessor.Params;

class HttpUrlTest {

    private static final String URL_WITHOUT_PARAM = "/user/create";
    private static final String URL = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    private static final String PATH = "/user/create";
    private static final Params PARAMS = Params.from("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");

    @Test
    public void construct() {
        HttpUrl httpUrl = HttpUrl.from(URL);
        assertThat(httpUrl.getPath()).isEqualTo(PATH);
        assertThat(httpUrl.getParams()).usingRecursiveComparison().isEqualTo(PARAMS);
    }

    @Test
    public void constructWithoutParam() {
        HttpUrl httpUrl = HttpUrl.from(URL_WITHOUT_PARAM);
        assertThat(httpUrl.getPath()).isEqualTo(PATH);
        assertThat(httpUrl.getParams().getParams()).isEmpty();
    }
}
