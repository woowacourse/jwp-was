package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import model.general.ContentType;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;
import utils.FileIoUtils;

public class ResourceController extends AbstractController {

    private static final String TEMPLATE_LOCATION = "./templates";
    private static final String STATIC_LOCATION = "./static";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Method method = httpRequest.getMethod();

        if (method.equals(Method.GET)) {
            return doGet(httpRequest);
        }

        return super.service(httpRequest);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        ContentType contentType = ContentType.of(httpRequest)
            .orElse(null);
        if (Objects.nonNull(contentType)) {
            return findResourceFile(httpRequest);
        }
        if (httpRequest.whetherUriHasExtension()) {
            return HttpResponse.of(Status.NOT_FOUND);
        }

        return super.doGet(httpRequest);
    }

    private HttpResponse findResourceFile(HttpRequest httpRequest) {
        StatusLine statusLine = StatusLine.of(httpRequest, Status.OK);
        Map<Header, String> headers = new HashMap<>();
        byte[] body;

        try {
            body = FileIoUtils.loadFileFromClasspath(generatePath(httpRequest));
        } catch (Exception e) {
            return HttpResponse.of(Status.NOT_FOUND);
        }

        ContentType contentType = ContentType.of(httpRequest)
            .orElse(null);
        headers.put(Header.CONTENT_TYPE, contentType.getContentTypeValue());
        headers.put(Header.CONTENT_LENGTH, String.valueOf(body.length));

        return HttpResponse.of(statusLine, headers, body);
    }

    private static String generatePath(HttpRequest httpRequest) {
        ContentType contentType = ContentType.of(httpRequest)
            .orElse(null);
        String location = httpRequest.getRequestUri();

        if (contentType.equals(ContentType.HTML) || contentType.equals(ContentType.ICO)) {
            return TEMPLATE_LOCATION + location;
        }

        return STATIC_LOCATION + location;
    }
}
