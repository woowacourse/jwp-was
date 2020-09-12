package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import request.QueryData;

class QueryDataTest {

    private QueryData queryData = new QueryData("id=abcd&password=1234");

    @Test
    void isUriUsingQuery() {
        String uriUsingQuery = "/join?id=abcd&password=1234";
        assertThat(QueryData.isUriUsingQuery(uriUsingQuery)).isTrue();

        String uriNotUsingQuery = "/join";
        assertThat(QueryData.isUriUsingQuery(uriNotUsingQuery)).isFalse();
    }

    @Test
    void getQueryData() {
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "abcd");
        expected.put("password", "1234");

        assertThat(queryData.getQueryData()).isEqualTo(expected);
    }
}