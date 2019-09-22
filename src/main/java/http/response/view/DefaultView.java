package http.response.view;

import http.ContentType;
import http.HTTP;
import http.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.UrlNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultView extends View {
    private static final Logger logger = LoggerFactory.getLogger(DefaultView.class);

    public DefaultView(String path) throws IOException, URISyntaxException, UrlNotFoundException {
        this.body = findBody(path);
        header.put(HTTP.CONTENT_TYPE, ContentType.valueByPath(path).getContents() + ";charset=utf-8");
        header.put(HTTP.CONTENT_LENGTH, String.valueOf(body.length));
    }

    @Override
    public String getHeader() {
        return super.getHeader(ResponseStatus.OK);
    }

    private byte[] findBody(String path) throws IOException, URISyntaxException, UrlNotFoundException {
        return FileIoUtils.loadFileFromClasspath(path);
    }
}
