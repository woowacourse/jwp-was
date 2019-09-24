package http.application;

import http.request.ContentType;
import http.request.HttpRequest;
import http.request.MessageBodyParser;
import http.request.bodyparser.FormDataParser;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);
    private static final String CONTENT_TYPE = "Content-Type";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        ContentType contentType = ContentType.of(httpRequest.getHttpHeader().get(CONTENT_TYPE));
        MessageBodyParser bodyParser = new FormDataParser();

        Map<String, String> formData = bodyParser.parse(httpRequest.getHttpRequestBody());

        User user = new User(formData.get("userId"), formData.get("password"),
                formData.get("name"), formData.get("email"));

        logger.info("user created: {}", user);
        httpResponse.redirect("/index.html");
    }
}
