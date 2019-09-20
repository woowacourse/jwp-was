package webserver;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
        HttpResponse res = HttpResponse.ResponseBuilder.createBuilder()
            .withStatus(HttpStatus.OK)
            .withHeaders(headers)
            .withBody("This is body".getBytes())
            .build();
        ResponseWriter rw = new ResponseWriter(dos);
        rw.write(res);

        assertThat(baos.toString()).isEqualTo("HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/plain\r\n" +
            "\r\n" +
            "This is body");
    }
}
