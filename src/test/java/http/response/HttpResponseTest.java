package http.response;

import http.ContentType;
import http.StatusCode;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class HttpResponseTest {
    @Test
    void response200Header() throws IOException {
        IDataOutputStream dos = mock(IDataOutputStream.class);
        HttpResponse response = HttpResponse.of(dos);
        int expectedLengthOfBodyContent = 10;
        List<String> expectedHeader = Arrays.asList(
                "HTTP/1.1 200 OK \r\n",
                "Content-Type: text/html;charset=utf-8\r\n",
                "Content-Length: " + expectedLengthOfBodyContent + "\r\n",
                "\r\n");

        // builder 사용해서 타입 정하고 charset 정하면 좋을듯..!
        response.setHeader("Content-Type", ContentType.HTML.toHeaderValue());
        response.setHeader("Content-Length", Integer.toString(expectedLengthOfBodyContent));
        response.responseHeader(StatusCode.OK);

        // [TODO] 검증 방식을 바꿔야 함
        for (String line : expectedHeader) {
            System.out.println(line);
            verify(dos).writeBytes(line);
        }
    }

    @Test
    void responseBody() throws IOException {
        IDataOutputStream dos = mock(IDataOutputStream.class);
        byte[] expectedBody = new byte[]{1, 2, 3};
        HttpResponse response = HttpResponse.of(dos);

        response.responseBody(expectedBody);

        InOrder inOrder = inOrder(dos);
        inOrder.verify(dos).write(expectedBody, 0, expectedBody.length);
        inOrder.verify(dos).flush();
    }
}