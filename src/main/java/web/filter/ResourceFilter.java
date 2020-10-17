package web.filter;

import utils.FileIoUtils;
import web.HeaderName;
import web.HttpRequest;
import web.HttpResponse;
import web.HttpStatusCode;

import java.io.IOException;

public class ResourceFilter implements Filter {

    @Override
    public void doFilter(HttpRequest httpRequest, HttpResponse httpResponse, FilterChain filterChain) throws IOException {

        String requestPath = httpRequest.getRequestPath();
        if (ResourceMatcher.isResource(requestPath) && httpRequest.isGetMethod()) {
            ResourceMatcher matcher = ResourceMatcher.findResourceMatcher(requestPath);

            byte[] body = FileIoUtils.loadFileFromClasspath(matcher.getResourcePath() + requestPath);

            httpResponse.putHeader(HeaderName.CONTENT_TYPE.getName(), matcher.getContentType());
            httpResponse.response(HttpStatusCode.OK, body);
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }

}