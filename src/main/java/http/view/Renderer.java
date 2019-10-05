package http.view;

import http.model.HttpResponse;
import http.model.HttpStatus;
import http.supoort.ResponseMessageConverter;

import java.io.DataOutputStream;
import java.io.IOException;

public class Renderer {
    public static void render(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        String header = ResponseMessageConverter.convertHeader(httpResponse);
        if (httpResponse.isSameStatus(HttpStatus.FOUND)) {
            dos.writeBytes(header);
            dos.flush();
            return;
        }
        dos.writeBytes(header);
        dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
        dos.flush();
    }
}
