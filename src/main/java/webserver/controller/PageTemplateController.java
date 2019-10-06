package webserver.controller;

import http.ContentType;
import http.ContentTypeFactory;
import http.NotAcceptableException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.page.Page;
import webserver.pageprovider.PageProvider;
import webserver.pageprovider.PageProviderRequest;
import webserver.pageprovider.PageProviderResponse;

import java.util.Objects;

public class PageTemplateController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(PageTemplateController.class);

    private final PageProvider getPageProvider;
    private final PageProvider postPageProvider;

    private PageTemplateController(PageProvider getPageProvider, PageProvider postPageProvider) {
        this.getPageProvider = getPageProvider;
        this.postPageProvider = postPageProvider;
    }

    public static PageTemplateController ofGetPageProvider(PageProvider getPageProvider) {
        return new PageTemplateController(getPageProvider, null);
    }

    public static PageTemplateController ofPostPageProvider(PageProvider postPageProvider) {
        return new PageTemplateController(null, postPageProvider);
    }

    public static PageTemplateController fromPageProviders(PageProvider getPageProvider, PageProvider postPageProvider) {
        return new PageTemplateController(getPageProvider, postPageProvider);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if (getPageProvider == null) {
            super.doGet(request, response);
            return;
        }
        doMethod(getPageProvider, request, response);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        if (getPageProvider == null) {
            super.doGet(request, response);
            return;
        }
        doMethod(getPageProvider, request, response);
    }

    private void doMethod(PageProvider pageProvider, HttpRequest request, HttpResponse response) {
        try {
            tryDo(pageProvider, request, response);
        } catch (RuntimeException e) {
            log.error("runtime error: ", e);

            // reset response
            // [TODO] use HandlebarsTemplatePage
        }
    }

    private void tryDo(PageProvider pageProvider, HttpRequest request, HttpResponse response) {
        Page page = pageProvider.provide(PageProviderRequest.from(request), PageProviderResponse.from(response));
        if (page.isRedirectPage()) {
            response.setHeader("Location", page.getLocation());
            response.response302Header();
            return;
        }

        validateContentType(request, page.getContentType());
        response.setHeader("Content-Type", page.getContentType().toHeaderValue());

        byte[] body = page.getBody();
        response.setHeader("Content-Length", Integer.toString(body.length));

        response.response200Header();
        response.responseBody(body);
    }

    private void validateContentType(HttpRequest request, ContentType wantedContentType) {
        String accept = request.getHeader("Accept");
        if (!ContentTypeFactory.canCreate(accept, wantedContentType)) {
            throw NotAcceptableException.from(accept);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageTemplateController that = (PageTemplateController) o;
        return Objects.equals(getPageProvider, that.getPageProvider) &&
                Objects.equals(postPageProvider, that.postPageProvider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageProvider, postPageProvider);
    }
}
