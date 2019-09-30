package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.ModelAndView;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static void createResponse(HttpResponse httpResponse, ModelAndView modelAndView, OutputStream out) {
        httpResponse.updateStatusLine(modelAndView.getHttpStatus());
        httpResponse.updateResponseHeader(modelAndView);
        httpResponse.updateResponseBody(modelAndView);

        flushDataOutputStream(httpResponse, out);
    }

    private static void flushDataOutputStream(HttpResponse httpResponse, OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeBytes(httpResponse.getStatusLine().toString() + "\r\n");
            dos.writeBytes(httpResponse.getResponseHeader().toString());
            dos.writeBytes("\r\n");
            dos.write(httpResponse.getResponseBody(), 0, httpResponse.getResponseBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
