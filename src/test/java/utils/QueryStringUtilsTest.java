package utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringUtilsTest {

    @Test
    void parse_query() {
        String query = "userId=javajigi&password=password&name=JaeSung";
        Map<String, String> info = new HashMap<>();
        info.put("userId", "javajigi");
        info.put("password", "password");
        info.put("name", "JaeSung");

        assertThat(QueryStringUtils.parse(query)).isEqualTo(info);


    }


}