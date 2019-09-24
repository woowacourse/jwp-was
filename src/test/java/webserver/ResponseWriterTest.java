package webserver;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseWriterTest {

    @Test
    void write_response() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        HttpRequest req = new HttpRequest(HttpMethod.GET, "/", "/", new HashMap<>(), new HashMap<>(),
            new HashMap<>(), Collections.emptyMap());
        HttpResponse res = new HttpResponse();
        res.setStatus(HttpStatus.OK);
        res.addHeader("Content-Type", "text/plain");
        res.setBody("This is body".getBytes());
        ResponseWriter rw = new ResponseWriter(dos);
        rw.write(req, res);

        assertThat(baos.toString()).isEqualTo("HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/plain\r\n" +
            "\r\n" +
            "This is body");
    }

    @Test
    void write_cookie() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        HttpRequest req = new HttpRequest(HttpMethod.GET, "/", "/", new HashMap<>(), new HashMap<>(),
            new HashMap<>(), Collections.emptyMap());
        HttpResponse res = new HttpResponse();
        res.setStatus(HttpStatus.OK);
        res.addCookie("logined", "true");
        ResponseWriter rw = new ResponseWriter(dos);
        rw.write(req, res);

        assertThat(baos.toString()).contains("HTTP/1.1 200 OK\r\n" +
            "Set-Cookie: logined=true");
    }
}
