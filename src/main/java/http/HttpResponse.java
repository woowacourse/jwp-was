package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTTP_VERSION = "HTTP/1.1";

    private final OutputStream out;

    public HttpResponse(final OutputStream out) {
        this.out = out;
    }

    public void forward(String resource) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            byte[] body = FileIoUtils.loadFileFromClasspath(resource);

            final String extension = resource.substring(resource.lastIndexOf(".") + 1);
            String contentType = MimeType.getType(extension);
            response200Header(dos, contentType, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            response302Header(dos, location);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(final DataOutputStream dos, final String location) throws IOException {
        dos.writeBytes(String.format("%s %s %s\n", HTTP_VERSION, HttpStatus.FOUND.getCode(), HttpStatus.FOUND.getPhrase()));
        dos.writeBytes("Location: " + location);
        dos.writeBytes("\r\n");
    }

    private void response200Header(DataOutputStream dos, String contentType, int contentLength) throws IOException {
        dos.writeBytes(String.format("%s %s %s\n", HTTP_VERSION, HttpStatus.OK.getCode(), HttpStatus.OK.getPhrase()));
        dos.writeBytes("Content-Type: " + contentType + "\r\n");
        dos.writeBytes("Content-Length: " + contentLength + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }


//    public void write() {
//        try (DataOutputStream dos = new DataOutputStream(out)) {
//            dos.writeBytes(String.format("%s %s %s\n", httpVersion, httpStatus.getCode(), httpStatus.getPhrase()));
//            for (String key : headers.keySet()) {
//                dos.writeBytes(String.format("%s: %s\n", key, headers.get(key)));
//                if (body != null) {
//                    dos.writeBytes("Content-Length: " + body.length);
//                }
//            }
//            dos.writeBytes("\n");
//            dos.write(body, 0, body.length);
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    public HttpStatus getHttpStatus() {
//        return httpStatus;
//    }
//
//    public String getHttpVersion() {
//        return httpVersion;
//    }

//    public static HttpResponseBuilder builder(OutputStream out) {
//        return new HttpResponseBuilder(out);
//    }
//
//    public static class HttpResponseBuilder {
//        private final OutputStream out;
//        private HttpStatus httpStatus;
//        private String httpVersion;
//        private Map<String, String> headers = new HashMap<>();
//        private byte[] body;
//
//        public HttpResponseBuilder(final OutputStream out) {
//            this.out = out;
//        }
//
//        public HttpResponse.HttpResponseBuilder httpStatus(final HttpStatus httpStatus) {
//            this.httpStatus = httpStatus;
//            return this;
//        }
//
//        public HttpResponse.HttpResponseBuilder httpVersion(final String httpVersion) {
//            this.httpVersion = httpVersion;
//            return this;
//        }
//
//        public HttpResponse.HttpResponseBuilder header(final String key, final String value) {
//            this.headers.put(key, value);
//            return this;
//        }
//
//        public HttpResponse.HttpResponseBuilder body(final byte[] body) {
//            this.body = body;
//            return this;
//        }
//
//        public HttpResponse build() {
//            return new HttpResponse(this.httpStatus, this.httpVersion, this.headers, this.body, this.out);
//        }
//    }
}
