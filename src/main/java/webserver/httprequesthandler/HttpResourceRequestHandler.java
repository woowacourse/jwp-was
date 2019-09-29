package webserver.httprequesthandler;

import com.github.jknack.handlebars.io.TemplateLoader;
import controller.exception.MethodNotAllowedException;
import http.common.ContentTypeMapper;
import http.request.HttpRequest;
import http.request.RequestMethod;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class HttpResourceRequestHandler extends AbstractHttpRequestHandler {
    private final TemplateLoader fileLoader;

    public HttpResourceRequestHandler(TemplateLoader fileLoader) {
        this.fileLoader = fileLoader;
        fileLoader.setPrefix("/static");
        fileLoader.setSuffix("");
    }

    @Override
    public void handleInternal(HttpRequest httpRequest, HttpResponse httpResponse) {
        checkRequestMethod(httpRequest.getMethod());
        try {
            String filePath = httpRequest.getPath();
            byte[] file = fileLoader.sourceAt(filePath).content(Charset.defaultCharset()).getBytes();
            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute("Content-Type", ContentTypeMapper.getContentType(filePath));
            httpResponse.addHeaderAttribute("Content-Length", String.valueOf(file.length));
            httpResponse.setBody(file);
        } catch (IOException e) {
            e.printStackTrace();
            httpResponse.setResponseStatus(ResponseStatus.NOT_FOUND);
        }
    }

    private void checkRequestMethod(RequestMethod method) {
        if (!RequestMethod.GET.equals(method)) {
            throw new MethodNotAllowedException();
        }
    }

    @Override
    public boolean canHandle(String path) {
        String[] url = StringUtils.split(path, "/");
        return StringUtils.isNotBlank(url) && url[url.length - 1].matches("^[^/:*?<>|\"\\\\]+[.][a-zA-Z0-9]+$");
    }
}
