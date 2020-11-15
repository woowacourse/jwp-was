package net.slipp.presentation;

import static com.google.common.base.Charsets.*;
import static kr.wootecat.dongle.application.http.MimeType.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.slipp.application.UserService;
import net.slipp.config.ServiceFactory;
import net.slipp.presentation.dto.UsersResponse;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import kr.wootecat.dongle.application.http.request.HttpRequest;
import kr.wootecat.dongle.application.http.response.HttpResponse;
import kr.wootecat.dongle.application.servlet.HttpServlet;
import utils.FileIoUtils;

public class UserListServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private static final String REQUIRE_AUTH_PAGE_URL = "/login.html";
    private static final String LOGINED = "logined";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        UserService userService = ServiceFactory.getUserService();
        UsersResponse userResponse = userService.findAll();

        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("user/list");
            String userListPage = template.apply(userResponse);
            response.addBody(userListPage.getBytes(UTF_8), HTML_UTF_8);
            logger.debug("{}", userListPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
