package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;

import webserver.exception.FailedForwardException;
import webserver.exception.UnauthorizedRequestException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseGenerator;

import static webserver.controller.CreateUserController.JSESSION_ID;
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
            HttpResponse httpResponse = HttpResponseGenerator.response200Header(requestUri);

            if (!httpRequest.hasCookieValue(JSESSION_ID)) {
                String uuid = sessionManager.generateInitialSession();
                httpResponse.setInitialSession(uuid);
            }

            httpResponse.forward(dos);
        } catch (IOException e) {
            throw new FailedForwardException();
        }
    }
}
