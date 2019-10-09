package webserver;

import http.HttpVersion;
import http.response.HttpResponse;
import http.response.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;


public class ResponseWriter {
    private static final Logger log = LoggerFactory.getLogger(ResponseWriter.class);
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String BLANK = " ";
    private static final String NEW_LINE = "\r\n";
    private static final String COLON = ": ";

    private HttpResponse response;
    private DataOutputStream dos;

    ResponseWriter(HttpResponse response, DataOutputStream dos) {
        this.response = response;
        this.dos = dos;
    }

    private void writeStartLine() throws IOException {
        HttpVersion version = response.getVersion();
        StatusCode status = response.getStatus();

        String line = version + BLANK
                + status.getStatusValue() + BLANK
                + status.getStatus();

        log.debug("{}", line + NEW_LINE);
        dos.writeBytes(line + NEW_LINE);
    }

    private void writeHeader() {
        try {
            for (String line : response.getHeaderKeys()) {
                log.debug("Response header {}", line + COLON + response.getHeaderValue(line) + NEW_LINE);
                dos.writeBytes(line + COLON + response.getHeaderValue(line) + NEW_LINE);
            }
        } catch (IOException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    private void writeCookie() {
        if (response.hasCookie()) {
            try {
                dos.writeBytes(SET_COOKIE + COLON + response.getCookieValues() + NEW_LINE);
            } catch (IOException e) {
                throw new ServerErrorException(e.getMessage());
            }
        }
    }

    private void writeBody() {
        byte[] body = response.getBody();

        try {
            dos.writeBytes(NEW_LINE);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    void write() throws IOException {
        writeStartLine();
        writeHeader();
        writeCookie();
        writeBody();
    }
}
