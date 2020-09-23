package webserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

class HttpResponseTest {
    private final String testDirectory = "./src/test/resources/";

    @Test
    void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(HttpStatus.OK, createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
