package httpmethod;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class PostTest {
    @Test
    void create() throws IOException {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Length", "72");
        String request = "userId=javajigi&password=password&name=javajigi&email=javajigi@slipp.net";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        Post post = new Post();
        Map<String, String> parameter = post.extractParameter(br, header);

        assertAll(
            () -> assertThat(parameter.get("userId")).isEqualTo("javajigi"),
            () -> assertThat(parameter.get("password")).isEqualTo("password"),
            () -> assertThat(parameter.get("name")).isEqualTo("javajigi"),
            () -> assertThat(parameter.get("email")).isEqualTo("javajigi@slipp.net")
        );
    }
}
