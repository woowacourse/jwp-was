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

    public Map<String, String> makePostHeader(String... values) {
        Map<String, String> header = new HashMap<>();
        List<String> keys = Arrays.asList("Request-Line:", "Content-Length:", "Content-Type:", "Query-Parameters:");
        for (int i = 0; i < values.length; i++) {
            header.put(keys.get(i), values[i]);
        }
        return header;
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

    public Map<String, String> makeGetHeader(String... values) {
        Map<String, String> header = new HashMap<>();
        List<String> keys = Arrays.asList("Request-Line:");
        for (int i = 0; i < values.length; i++) {
            header.put(keys.get(i), values[i]);
        }
        return header;
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

    //        RequestMethod method = RequestMethod.GET;
//        RequestUrl url = RequestUrl.from("/index.html");
//        Map<String, String > header = makeGetHeader("GET /index.html HTTP/1.1");
//
//        RequestInformation requestInformation = new RequestInformation(header);
//        Request request = new Request(method, url, requestInformation);
//


}
