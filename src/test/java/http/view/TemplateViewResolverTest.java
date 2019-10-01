package http.view;

import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class TemplateViewResolverTest {
    private String testDirectory = "./src/test/resources/";
    private HttpRequest request;

    @BeforeEach
    void setUp() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        request = new HttpRequest(in);
    }

    @Test
    void render() throws Exception {
        List<User> users = Arrays.asList(
                new User("javajigi", "password", "자바지기", "javajigi@slipp.net"),
                new User("sanjigi", "password", "산지기", "sanjigi@slipp.net")
        );
        request.addAttribute("users", users);
        HttpResponse response = new HttpResponse(request, createOutputStream("list.html"));
        TemplateViewResolver viewResolver = new TemplateViewResolver();
        View view = viewResolver.resolveViewName("/user/list.html");
        view.render(request, response);
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
