package test;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import http.session.Session;
import http.session.SessionRepository;
import http.session.sessionkeygenerator.UUIDSessionKeyGenerator;
import model.User;

import java.io.IOException;
import java.util.*;

public class BaseTest {

    public byte[] createWithTemplateEngine() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("user/list");
        Map<String, List<User>> users = new HashMap<>();
        List<User> userList = new ArrayList<>(DataBase.findAll());
        users.put("users", userList);

        String listPage = template.apply(users);
        byte[] confirmBody = listPage.getBytes();
        return confirmBody;
    }


    public Request createPostRequest(RequestUrl url, List<String> headerValues) {
        RequestMethod method = RequestMethod.POST;
        List<String> headerKeys = Arrays.asList("Request-Line:", "Content-Length:", "Content-Type:", "Query-Parameters:");

        Map<String, String> header = new HashMap<>();

        for (int i = 0, n = headerValues.size(); i < n; i++) {
            header.put(headerKeys.get(i), headerValues.get(i));
        }

        RequestInformation requestHeader = new RequestInformation(header);

        return new Request(method, url, requestHeader);
    }


    public Request createGetRequest(RequestUrl url, List<String> headerValues) {
        RequestMethod method = RequestMethod.GET;
        List<String> headerKeys = Arrays.asList("Request-Line:", "Content-Length:", "Content-Type:", "Query-Parameters:");

        Map<String, String> header = new HashMap<>();
        for (int i = 0, n = headerValues.size(); i < n; i++) {
            header.put(headerKeys.get(i), headerValues.get(i));
        }

        RequestInformation requestHeader = new RequestInformation(header);
        return new Request(method, url, requestHeader);
    }

    public Session signUpAndLogin() {
        User user1 = new User("easy", "easy", "easy", "easy@gmail.com");
        User user2 = new User("kjm", "kjm", "kjm", "kjm@gmail.com");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
        Session session = SessionRepository.getInstance().createSession(new UUIDSessionKeyGenerator());
        session.setAttribute("user", user2.getUserId());

        return session;
    }

    public Request createGetRequestWithSession(String sessionId) {
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/user/list");
        Map<String, String> header = new HashMap<>();
        header.put("Request-Line:", "GET /user/list HTTP/1.1");
        header.put("Cookie:", "Session-Id=" + sessionId);
        RequestInformation requestInformation = new RequestInformation(header);

        return new Request(method, url,requestInformation);
    }

}
