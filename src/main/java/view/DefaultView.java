package view;

import http.ContentType;
import http.HTTP;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultView extends View {
    private static final Logger logger = LoggerFactory.getLogger(DefaultView.class);

    public DefaultView(String path) throws IOException, URISyntaxException {
        this.body = findBody(path);
        header.put(HTTP.CONTENT_TYPE, ContentType.valueByPath(path).getContents() + ";charset=utf-8");
        header.put(HTTP.CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void addHeader(HTTP key, String value) {
        header.put(key, value);
    }

    @Override
    public String getHeader() {
        return super.getHeader(HttpStatus.OK);
    }

    private byte[] findBody(String path) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + path);

        if (body == null) {
            body = FileIoUtils.loadFileFromClasspath("./static" + path);
        }
        return body;
    }
}
