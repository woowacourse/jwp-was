package http.response.view;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectViewTest {

    @Test
    void js문서_get요청_response_header_확인() throws IOException, URISyntaxException {
        String path = "/index.html";
        View view = new RedirectView(path);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        setHeader(path, byteArrayOutputStream);
        assertThat(view.getHeader().getBytes()).isEqualTo(byteArrayOutputStream.toByteArray());
    }

    private void setHeader(String path, ByteArrayOutputStream byteArrayOutputStream) throws IOException, URISyntaxException {
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeBytes("HTTP/1.1 302 Found\r\n");
        dataOutputStream.writeBytes("Location: " + path + "\r\n");
        dataOutputStream.writeBytes("\r\n");
    }

}