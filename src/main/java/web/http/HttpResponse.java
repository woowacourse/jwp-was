package web.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import utils.FormatUtils;
import view.ModelAndView;
import web.StaticFile;
import web.controller.ExceptionHandler;
import web.session.Cookie;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private HttpResponseHeader httpResponseHeader;
    private byte[] body;

    public HttpResponse(OutputStream output) {
        dos = new DataOutputStream(output);
    }

    public void ok(String location) {
        try {
            this.body = FileIoUtils.loadFileFromClasspath(location);
        } catch (Exception e) {
            logger.info(e.getMessage());
            ExceptionHandler.processException(e, this);
            return;
        }
        completeOkHeader(StaticFile.of(location).getType());
    }

    public void ok(ModelAndView modelAndView) {
        try {
            this.body = modelAndView.render().getBytes();
        } catch (IOException e) {
            logger.info(e.getMessage());
            ExceptionHandler.processException(e, this);
            return;
        }
        completeOkHeader();
    }

    private void completeOkHeader(String type) {
        HttpResponseLine httpResponseLine = new HttpResponseLine(HttpStatus.OK);
        httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        httpResponseHeader.add("Content-Type", type + ";charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));
    }

    private void completeOkHeader() {
        HttpResponseLine httpResponseLine = new HttpResponseLine(HttpStatus.OK);
        httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));
    }

    public void redirect(String location) {
        HttpResponseLine httpResponseLine = new HttpResponseLine(HttpStatus.FOUND);
        httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        httpResponseHeader.add("Location", location);
    }

    public void exception(HttpStatus httpStatus, byte[] body) {
        HttpResponseLine httpResponseLine = new HttpResponseLine(httpStatus);
        httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        this.body = body;
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));
    }

    public void send() throws IOException {
        String responseLine = FormatUtils.formatResponseLine(httpResponseHeader.getHttpResponseLine());
        dos.writeBytes(responseLine);

        for (Map.Entry<String, Object> entry : httpResponseHeader.getHeaders().entrySet()) {
            dos.writeBytes(FormatUtils.formatHeader(entry));
        }

        if (hasBody()) {
            dos.writeBytes(System.lineSeparator());
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }

    private boolean hasBody() {
        return !Objects.isNull(body) && body.length > 0;
    }

    public byte[] getBody() {
        return body;
    }

    public void addCookie(Cookie cookie) {
        cookie.add("Path", "/");
        httpResponseHeader.add("Set-Cookie", cookie);
    }

    public void addHttpStatus(HttpStatus httpStatus) {
        this.httpResponseHeader = new HttpResponseHeader(new HttpResponseLine(httpStatus));
    }

    public Map<String, Object> getHeaders() {
        return Collections.unmodifiableMap(httpResponseHeader.getHeaders());
    }

    public HttpStatus getHttpStatus() {
        return httpResponseHeader.getHttpResponseLine().getHttpStatus();
    }
}
