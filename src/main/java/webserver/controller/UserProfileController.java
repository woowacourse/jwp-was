package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

public class UserProfileController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        Optional<User> loggedInUser = retrieveLoggedInUser(request);
        if (!loggedInUser.isPresent()) {
            response.setHeader("Location", "/user/login.html");
            response.response302Header();
            return;
        }

        // 로그인 된 상태
        log.error("loggedInUser: {}", loggedInUser.get());

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("user/profile");
            byte[] b = template.apply(loggedInUser.get()).getBytes("UTF-8");

            // contentType
            Tika tika = new Tika();
            String mimeType = tika.detect(new ByteArrayInputStream(b));
            ContentType contentType = ContentType.fromMimeType(mimeType).get();

            response.setHeader("Content-Type", contentType.toHeaderValue());
            response.setHeader("Content-Length", Integer.toString(b.length));
            response.response200Header();
            response.responseBody(b);
        } catch (IOException e) {
            log.error("error: {}", e);
        }
    }

    private Optional<User> retrieveLoggedInUser(HttpRequest request) {
        return request.getSession(false)
                .map(session -> (User) session.getAttribute("user"));
    }
}
