package web;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {

    @DisplayName("RequestBody 생성")
    @Test
    public void create() throws IOException {

        String input = "userId=javajigi&password=javable&name=pobi&email=test@test.com";

        BufferedReader br = new BufferedReader(new StringReader(input));
        RequestBody requestBody = RequestBody.of(br, String.valueOf(input.length()));

        assertThat(requestBody.getParams()).containsEntry("userId", "javajigi");
        assertThat(requestBody.getParams()).containsEntry("password", "javable");
        assertThat(requestBody.getParams()).containsEntry("name", "pobi");
        assertThat(requestBody.getParams()).containsEntry("email", "test@test.com");
    }

}