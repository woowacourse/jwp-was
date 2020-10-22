package webserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import utils.FileIoUtils;
import utils.RequestUtils;

class HttpResponseTest {
    @Test
    void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        String path = "/index.html";
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        byte[] body = FileIoUtils.findStaticFile(path);
        response.setHttpStatus(HttpStatus.OK);
        response.addHeader(HttpHeader.CONTENT_TYPE,
            String.format("text/%s;charset=utf-8", RequestUtils.extractExtension(path)));
        response.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length));
        response.forward(body);
    }

    @Test
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 header에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.setHttpStatus(HttpStatus.FOUND);
        response.sendRedirect("/index.html");
    }

    @Test
    public void error() throws IOException {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Error.txt"));
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.error();
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        String testDirectory = "./src/test/resources/";
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
