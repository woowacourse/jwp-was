package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class StringUtilsTest {

    private static final String PARAM_BUNDLE = "userId=javajigi&password=password&name=포비&email=javajigi@slipp.net";
    private static final Map<String, String> PARAMS = new HashMap<String, String>() {{
        put("userId", "javajigi");
        put("password", "password");
        put("name", "포비");
        put("email", "javajigi@slipp.net");
    }};

    @Test
    public void extractParams() {
        Map<String, String> params = StringUtils.extractParams(PARAM_BUNDLE);
        assertThat(params).isEqualTo(PARAMS);
    }
}