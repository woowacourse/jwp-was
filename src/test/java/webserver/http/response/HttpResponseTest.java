package webserver.http.response;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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