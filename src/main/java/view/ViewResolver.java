package view;

import java.io.IOException;
import java.net.URISyntaxException;

import http.HttpStatus;
import http.HttpVersion;
import http.StaticFile;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import utils.FileIoUtils;

public class ViewResolver {

    public HttpResponse resolve(ModelAndView modelAndView) throws IOException, URISyntaxException {
        View view = modelAndView.getView();
        String resource = view.getUri();
        ResponseHeader responseHeader = new ResponseHeader();

        if (view.isRedirectView()) {
            responseHeader.addRedirectHeader(resource);
            return HttpResponse.from(responseHeader, HttpStatus.FOUND, HttpVersion.HTTP1_1);
        }

        return resolveResponse(resource, responseHeader);
    }

    private HttpResponse resolveResponse(String resource, ResponseHeader responseHeader) throws
        IOException,
        URISyntaxException {
        StaticFile staticFile = StaticFile.findStaticFile(resource);
        byte[] body = FileIoUtils.loadFileFromClasspath(staticFile.getResourcePath() + resource);

        responseHeader.addHeader("Content-Type", staticFile.getContentType());
        responseHeader.addHeader("Content-Length", String.valueOf(body.length));

        return HttpResponse.from(responseHeader, HttpStatus.OK, HttpVersion.HTTP1_1, body);
    }

}
