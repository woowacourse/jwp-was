package web.filter;

import utils.FileIoUtils;
import web.HttpRequest;
import web.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ResourceFilter implements Filter {

    @Override
    public void doFilter(HttpRequest httpRequest, HttpResponse httpResponse, FilterChain filterChain) {

        String requestPath = httpRequest.getRequestPath();
        if (ResourceMatcher.isResource(requestPath) && httpRequest.getMethod().isGet()) {
            ResourceMatcher filter = ResourceMatcher.findResourceFilter(requestPath);

            byte[] body = filter.getBody(requestPath);

            httpResponse.response200Header(body.length, filter.getContentType());
            httpResponse.responseBody(body);
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }

    private enum ResourceMatcher {
        HTML(".html", "./templates", "text/html;charset=UTF-8"),
        JAVASCRIPT(".js", "./static", "application/javascript;charset=UTF-8"),
        CSS(".css", "./static", "text/css;charset=UTF-8");

        private final String suffix;
        private final String resourcePath;
        private final String contentType;

        ResourceMatcher(final String suffix, final String resourcePath, final String contentType) {
            this.suffix = suffix;
            this.resourcePath = resourcePath;
            this.contentType = contentType;
        }

        public static ResourceMatcher findResourceFilter(String requestPath) {
            return Arrays.stream(values())
                    .filter(resourceMatcher -> requestPath.endsWith(resourceMatcher.suffix))
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
        }

        public static boolean isResource(String requestPath) {
            return Arrays.stream(values())
                    .anyMatch(resourceMatcher -> requestPath.endsWith(resourceMatcher.suffix));
        }

        public byte[] getBody(String requestPath) {
            try {
                return FileIoUtils.loadFileFromClasspath(this.resourcePath + requestPath);
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException();
            }
        }

        public String getContentType() {
            return contentType;
        }
    }

}