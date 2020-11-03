package web;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.http.HttpRequestParams;

class HttpRequestParamsTest {

    @DisplayName("RequestBody 생성")
    @Test
    public void create() throws IOException {

        String input = "userId=javajigi&password=javable&name=pobi&email=test@test.com";

        BufferedReader br = new BufferedReader(new StringReader(input));
        HttpRequestParams httpRequestParams = new HttpRequestParams();
        httpRequestParams.addBody(br.readLine());

        assertThat(httpRequestParams.getParams()).containsEntry("userId", "javajigi");
        assertThat(httpRequestParams.getParams()).containsEntry("password", "javable");
        assertThat(httpRequestParams.getParams()).containsEntry("name", "pobi");
        assertThat(httpRequestParams.getParams()).containsEntry("email", "test@test.com");
    }

}