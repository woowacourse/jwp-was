package web.filter;

import utils.FileIoUtils;
import web.HttpRequest;
import web.HttpResponse;

import java.io.IOException;

public class ResourceFilter implements Filter {

    @Override
    public void doFilter(HttpRequest httpRequest, HttpResponse httpResponse, FilterChain filterChain) throws IOException {

        String requestPath = httpRequest.getRequestPath();
        if (ResourceMatcher.isResource(requestPath) && httpRequest.isGetMethod()) {
            ResourceMatcher matcher = ResourceMatcher.findResourceMatcher(requestPath);

            byte[] body = FileIoUtils.loadFileFromClasspath(matcher.getResourcePath() + requestPath);

            httpResponse.response200Header(body.length, matcher.getContentType());
            httpResponse.responseBody(body);
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }

}