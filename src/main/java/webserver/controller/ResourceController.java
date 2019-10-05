package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;

import webserver.exception.FailedForwardException;
import webserver.exception.UnauthorizedRequestException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseGenerator;
import webserver.utils.FileExtension;
import webserver.view.ModelAndView;

import static webserver.http.request.HttpRequestReader.REQUEST_URI;

public class ResourceController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
        throw new UnauthorizedRequestException();
    }

    @Override
    public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
        try {
            String requestUri = httpRequest.getRequestLineElement(REQUEST_URI);

            ModelAndView modelAndView = new ModelAndView(requestUri);

            HttpResponse httpResponse = HttpResponseGenerator.response200Header(modelAndView);

            if (isHtmlFile(requestUri) && !httpRequest.hasSessionId()) {
                String uuid = sessionManager.generateInitialSession();
                httpResponse.setInitialSession(uuid);
            }

            httpResponse.forward(dos);
        } catch (IOException e) {
            throw new FailedForwardException();
        }
    }

    private boolean isHtmlFile(String requestUri) {
        return requestUri.contains(FileExtension.HTML.getFileExtension());
    }
}
