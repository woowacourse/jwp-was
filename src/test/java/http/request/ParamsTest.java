package http.request;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ParamsTest {

    private static final String PARAM_BUNDLE = "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";
    private static final Map<String, String> PARAMS = new HashMap<String, String>() {{
        put("userId", "javajigi");
        put("password", "password");
        put("name", "박재성");
        put("email", "javajigi@slipp.net");
    }};

    @Test
    public void from() {
        Params params = Params.from(PARAM_BUNDLE);
        assertThat(params.getParams()).isEqualTo(PARAMS);
    }

    @ParameterizedTest
    @CsvSource({"userId,javajigi", "notExist,"})
    public void findValueBy(String key, String expected) {
        Params params = Params.from(PARAM_BUNDLE);
        assertThat(params.findValueBy(key)).isEqualTo(nullToEmpty(expected));

    }

    private String nullToEmpty(String input) {
        if (input == null) {
            return "";
        }
        return input;
    }
}