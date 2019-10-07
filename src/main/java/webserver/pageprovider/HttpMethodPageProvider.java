package webserver.pageprovider;

import http.NotSupportedHttpMethodException;
import http.request.HttpMethod;
import http.request.HttpRequestAccessor;
import http.response.HttpResponseAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.page.Page;

import java.util.HashMap;
import java.util.Map;

public class HttpMethodPageProvider implements PageProvider {
    private static final Logger log = LoggerFactory.getLogger(HttpMethodPageProvider.class);

    private final Map<HttpMethod, PageProvider> pageProviders;

    private HttpMethodPageProvider(Map<HttpMethod, PageProvider> pageProviders) {
        this.pageProviders = pageProviders;
    }

    public static HttpMethodPageProvider withGet(PageProvider pageProvider) {
        return new HttpMethodPageProvider(new HashMap<HttpMethod, PageProvider>() {{
            put(HttpMethod.GET, pageProvider);
        }});
    }

    public static HttpMethodPageProvider withPost(PageProvider pageProvider) {
        return new HttpMethodPageProvider(new HashMap<HttpMethod, PageProvider>() {{
            put(HttpMethod.POST, pageProvider);
        }});
    }

    public static HttpMethodPageProvider withMethod(HttpMethod method, PageProvider pageProvider) {
        return new HttpMethodPageProvider(new HashMap<HttpMethod, PageProvider>() {{
            put(method, pageProvider);
        }});
    }

    @Override
    public Page provide(HttpRequestAccessor request, HttpResponseAccessor response) {
        HttpMethod requestMethod = request.getHttpMethod();
        if (!pageProviders.containsKey(requestMethod)) {
            throw NotSupportedHttpMethodException.of(request.getPath());
        }

        return pageProviders.get(requestMethod).provide(request, response);
    }
}
