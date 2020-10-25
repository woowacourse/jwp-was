package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.FormatUtils;
import view.ModelAndView;
import webserver.controller.ExceptionHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String host = "http://localhost:8080";

    private final OutputStream outputStream;
    private HttpResponseHeader httpResponseHeader;
    private byte[] body;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void ok(String location) {
        try {
            this.body = FileIoUtils.loadFileFromClasspath(location);
        } catch (Exception e) {
            logger.info(e.getMessage());
            ExceptionHandler.processException(e, this);
            return;
        }
        ok();
    }

    public void ok(ModelAndView modelAndView) {
        try {
            this.body = modelAndView.render().getBytes();
        } catch (IOException e) {
            logger.info(e.getMessage());
            ExceptionHandler.processException(e, this);
            return;
        }
        ok();
    }

    private void ok() {
        HttpResponseLine httpResponseLine = new HttpResponseLine(HttpStatus.OK);
        httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));
    }

    public void setCookie(String value) {
        httpResponseHeader.add("Set-Cookie", value + " Path=/");
    }

    public void redirect(String location) {
        HttpResponseLine httpResponseLine = new HttpResponseLine(HttpStatus.FOUND);
        httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        httpResponseHeader.add("Location", host + location);
    }

    public void exception(HttpStatus httpStatus, byte[] body) {
        HttpResponseLine httpResponseLine = new HttpResponseLine(httpStatus);
        httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        this.body = body;
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));
    }

    public void send() throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String responseLine = FormatUtils.formatResponseLine(httpResponseHeader.getHttpResponseLine());
        dataOutputStream.writeBytes(responseLine);

        for (Map.Entry<String, String> entry : httpResponseHeader.getHeaders().entrySet()) {
            dataOutputStream.writeBytes(FormatUtils.formatHeader(entry));
        }

        if (hasBody()) {
            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.write(body, 0, body.length);
        }

        dataOutputStream.flush();
    }

    private boolean hasBody() {
        return body.length > 0;
    }

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(httpResponseHeader.getHeaders());
    }

    public HttpStatus getHttpStatus() {
        return httpResponseHeader.getHttpResponseLine().getHttpStatus();
    }
}
