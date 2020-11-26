package webserver.controller;

import static utils.TestIOUtils.createRequestBufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class ControllerTest {

    protected String service(String fileName, Controller controller) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        HttpRequest request = new HttpRequest(createRequestBufferedReader(fileName));
        HttpResponse response = new HttpResponse(byteArrayOutputStream);
        controller.service(request, response);

        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }
}
