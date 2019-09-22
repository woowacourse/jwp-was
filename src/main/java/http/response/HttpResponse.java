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

    public String getHeader() {
        StringBuffer sb = new StringBuffer();
        sb.append(HTTP.VERSION.getPhrase()).append(" ").append(view.getResponseStatus().getInfo()).append("\r\n");

        for (HTTP key : HTTP.values()) {
            if (view.checkHeader(key)) {
                sb.append(key.getPhrase()).append(": ").append(view.getHeaderContents(key)).append("\r\n");
            }
        }
        sb.append("\r\n");

        return sb.toString();
    }

    public byte[] getBody() {
        return view.getBody();
    }

    @Override
    public void close() throws IOException {
        writeHeader();
        writeBody();
        dataOutputStream.close();
    }

    private void writeBody() {
        try {
            dataOutputStream.write(view.getBody(), 0, view.getBody().length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader() {
        try {
            dataOutputStream.writeBytes(getHeader());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
