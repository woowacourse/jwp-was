package http.supoort.converter.response;

import http.exceptions.FailToConvertMessageException;
import http.model.response.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class AbstractViewResolver implements ViewResolver {
    private static final Logger log = LoggerFactory.getLogger(AbstractViewResolver.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String HTML_MIMETYPE = "text/html";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LINEBREAK = "\r\n";
    private static final String HEADER_SEPARATOR = ": ";

    @Override
    public void render(ServletResponse response, DataOutputStream dataOutputStream) {

        try {
            write(dataOutputStream, apply(response));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FailToConvertMessageException(e.getMessage());
        }
    }

    protected abstract String apply(ServletResponse response) throws IOException;

    private void write(DataOutputStream dataOutputStream, String body) throws IOException {
        dataOutputStream.writeBytes(CONTENT_TYPE + HEADER_SEPARATOR + HTML_MIMETYPE + LINEBREAK);
        dataOutputStream.writeBytes(CONTENT_LENGTH + HEADER_SEPARATOR + body.length() + LINEBREAK);
        dataOutputStream.writeBytes(LINEBREAK);
        dataOutputStream.write(body.getBytes());
        dataOutputStream.flush();
    }
}
