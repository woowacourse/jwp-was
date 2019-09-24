package http.request.factory;

import http.HttpRequestMethod;
import http.HttpVersion;
import http.MediaType;
import http.QueryString;
import http.request.HttpRequestStartLine;
import http.request.HttpRequestTarget;
import http.request.Resource;

public class HttpRequestStartLineFactory {

    public static HttpRequestStartLine create(String line) {
        String[] requestLine = line.split(" ");
        HttpRequestMethod httpRequestMethod = HttpRequestMethod.of(requestLine[0]);
        HttpRequestTarget httpRequestTarget = createRequestTarget(requestLine[1]);
        HttpVersion httpVersion = HttpVersion.of(requestLine[2]);

        return new HttpRequestStartLine(httpRequestMethod, httpRequestTarget, httpVersion);
    }

    private static HttpRequestTarget createRequestTarget(String resourcePath) {
        QueryString queryString = new QueryString("");

        if (resourcePath.contains("?")) {
            queryString = new QueryString(resourcePath.substring(resourcePath.indexOf("?") + 1));
            resourcePath = resourcePath.substring(0, resourcePath.indexOf("?"));
        }

        Resource resource = createResource(resourcePath);

        return new HttpRequestTarget(resource, queryString);
    }

    private static Resource createResource(String url) {
        String file = url.substring(url.lastIndexOf("/") + 1);
        String path = url.substring(0, url.lastIndexOf("/") + 1);

        MediaType mediaType = MediaType.of(file.substring(file.lastIndexOf(".") + 1));

        String fileName = file.split("." + mediaType.getExtension())[0];

        return new Resource(path, fileName, mediaType);
    }
}
