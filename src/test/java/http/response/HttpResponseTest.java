package http.response;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class HttpResponseTest {
    @Test
    void response200Header() throws IOException {
        DataOutputStream dos = mock(DataOutputStream.class);
        HttpResponse response = HttpResponse.of(dos);
        int expectedLenghtOfBodyContent = 10;
        List<String> expectedHeader = Arrays.asList(
                "HTTP/1.1 200 OK \r\n",
                "Content-Type: text/html;charset=utf-8\r\n",
                "Content-Length: " + expectedLenghtOfBodyContent + "\r\n",
                "\r\n");
// TODO: 2019-09-20
//        response.response200Header(expectedLenghtOfBodyContent);

        for(String line : expectedHeader) {
            verify(dos).writeBytes(line);
        }
    }

    @Test
    void responseBody() throws IOException {
        DataOutputStream dos = mock(DataOutputStream.class);
        byte[] expectedBody = new byte[]{1, 2, 3};
        HttpResponse response = HttpResponse.of(dos);

        response.writeBody(expectedBody);

        InOrder inOrder = inOrder(dos);
        inOrder.verify(dos).write(expectedBody, 0, expectedBody.length);
        inOrder.verify(dos).flush();
    }
}