package webserver.http.response;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    @Test
    void name() throws IOException {
        // given
        final OutputStream out = new ByteArrayOutputStream();

        final HttpResponse response = new HttpResponse(out);
        // when
//        response.forward("");
        // then

    }
}