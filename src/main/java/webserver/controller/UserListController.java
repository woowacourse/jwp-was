package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.model.User;
import application.service.UserService;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.exception.FailedForwardException;
import webserver.exception.UnauthorizedRequestException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseGenerator;

import static webserver.http.request.HttpRequestReader.REQUEST_URI;

public class UserListController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
        throw new UnauthorizedRequestException();
    }

    @Override
    public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
        try {
            if (doseNotLogin(httpRequest)) {
                HttpResponse httpResponse = HttpResponseGenerator.response302Header("/user/login.html");
                httpResponse.sendRedirect(dos);
            }

            String requestUri = httpRequest.getRequestLineElement(REQUEST_URI);
            HttpResponse httpResponse = HttpResponseGenerator.dynamicResponse200Header(generateDynamicResource(), requestUri);

            httpResponse.forward(dos);
        } catch (IOException e) {
            throw new FailedForwardException();
        }
    }

    private boolean doseNotLogin(HttpRequest httpRequest) {
        return !httpRequest.hasCookieValue("logined");
    }

    private byte[] generateDynamicResource() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        Map<String, List<User>> users = new LinkedHashMap<>();
        users.put("users", UserService.findAllUsers());

        return template.apply(users).getBytes();
    }
}
