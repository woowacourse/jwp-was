package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dataOutputStream;

    public HttpResponse(final OutputStream out) {
        dataOutputStream = new DataOutputStream(out);
    }

    public void write(View view) {
        writeHeader(view.getHeader());
        writeBody(view.getBody());
    }

    private void writeBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader(String header) {
        try {
            dataOutputStream.writeBytes(header);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        dataOutputStream.close();
    }
}
