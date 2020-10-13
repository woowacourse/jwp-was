package service;

import static model.general.ContentType.HTML;
import static model.general.ContentType.ICO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import model.general.ContentType;
import model.general.Header;
import model.general.Status;
import model.request.Request;
import model.response.Response;
import model.response.StatusLine;
import utils.FileIoUtils;

public class ResourceService {

    private static final String TEMPLATE_LOCATION = "./templates";
    private static final String STATIC_LOCATION = "./static";

    public static Response execute(Request request) throws IOException, URISyntaxException {
        request.getRequestUri();
        StatusLine statusLine = StatusLine.of(request, Status.OK);
        Map<Header, String> headers = new HashMap<>();
        byte[] body = FileIoUtils.loadFileFromClasspath(generatePath(request));
        ContentType contentType = request.generateContentTypeFromRequestUri();

        headers.put(Header.CONTENT_TYPE, contentType.getContentTypeValue());
        headers.put(Header.CONTENT_LENGTH, String.valueOf(body.length));

        return Response.of(statusLine, headers, body);
    }

    public static String generatePath(Request request) {
        ContentType contentType = request.generateContentTypeFromRequestUri();
        String location = request.getRequestUri();

        if (HTML.equals(contentType) || ICO.equals(contentType)) {
            return TEMPLATE_LOCATION + location;
        }
        return STATIC_LOCATION + location;
    }
}
