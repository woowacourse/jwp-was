package http.response;

import http.HTTP;
import http.response.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private View view;

    private final DataOutputStream dataOutputStream;

    public HttpResponse(final OutputStream out) {
        dataOutputStream = new DataOutputStream(out);
    }

    public void render(View view) {
        this.view = view;
    }

    public void addHeader(HTTP http, String value) {
        view.addHeader(http, value);
    }

    public String getResponseHeader() {
        return view.getHeader();
    }

    public byte[] getResponseBody() {
        return view.getBody();
    }

    @Override
    public void close() throws IOException {
        writeHeader(getResponseHeader());
        writeBody(getResponseBody());
        dataOutputStream.close();
    }

    private void writeBody(byte[] body) throws IOException {

        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();

    }

    private void writeHeader(String header) throws IOException {

        dataOutputStream.writeBytes(header);

    }

}
