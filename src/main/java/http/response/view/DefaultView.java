package http.response.view;

import com.google.common.collect.Maps;
import http.ContentType;
import http.HTTP;
import http.response.ResponseStatus;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class DefaultView implements View {
    private final Map<HTTP, String> header = Maps.newHashMap();
    private final byte[] body;

    public DefaultView(String path) throws IOException, URISyntaxException {
        this.body = findBody(path);
        header.put(HTTP.CONTENT_TYPE, ContentType.valueByPath(path).getContents() + ";charset=utf-8");
        header.put(HTTP.CONTENT_LENGTH, String.valueOf(body.length));
    }

    private byte[] findBody(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }

    @Override
    public ResponseStatus getResponseStatus() {
        return ResponseStatus.OK;
    }

    @Override
    public Map<HTTP, String> getHeader() {
        return header;
    }

    @Override
    public byte[] getBody() {
        return body;
    }
}
