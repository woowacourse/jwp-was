package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.Controller;
import controller.ControllerFactory;
import db.DataBase;
import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import http.response.Response;
import http.response.ResponseHeaders;
import http.response.ResponseStatus;
import http.session.Session;
import http.session.SessionRepository;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.BaseTest;
import utils.FileIoUtils;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerIntegerationTest extends BaseTest {
    private ControllerFactory factory = new ControllerFactory();

    @Test
    @DisplayName("Get File 요청시 Response 확인")
    void getFileResponseTest() throws IOException, URISyntaxException {
        //given
        RequestUrl url = RequestUrl.from("/index.html");
        List<String> headerValues = Arrays.asList("GET /index.html HTTP/1.1");
        Request request = createGetRequest(url, headerValues);
        Response response = new Response();

        //when
        Controller controller = factory.mappingController(request);
        controller.processResponse(request, response);

        //then
        Map<String, String> testconfirmMap = new LinkedHashMap<>();
        testconfirmMap.put("Content-Type: ", "text/html");
        testconfirmMap.put("Content-Length: ", "7049");
        byte[] confirmBody = FileIoUtils.loadFileFromClasspath("../resources/templates/index.html");
        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.OK);
        assertThat(response.getResponseHeaders().getResponseHeaders()).isEqualTo(testconfirmMap);
        assertThat(response.getResponseBody().getBody()).isEqualTo(confirmBody);
    }

    @Test
    @DisplayName("Post user/create 요청시 Response 확인")
    void postResponseTest() throws IOException, URISyntaxException {

        RequestUrl url = RequestUrl.from("/user/create");
        List<String> headerValues = Arrays.asList("POST /user/create HTTP/1.1");
        Request request = createPostRequest(url, headerValues);
        Response response = new Response();

        Controller controller = factory.mappingController(request);

        controller.processResponse(request, response);

        Map<String, String> confirmMap = new LinkedHashMap<>();
        confirmMap.put("Location: ", "http://localhost:8080/index.html");

        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.FOUND);
        assertThat(response.getResponseHeaders().getResponseHeaders()).isEqualTo(confirmMap);
    }

    @Test
    @DisplayName("로그인 성공시 response 확인 테스트")
    void loginTest() throws IOException, URISyntaxException {
        // given
        User user = new User("kjm", "kjm", "kjm", "kjm@gmail.com");
        DataBase.addUser(user);

        // when
        RequestUrl url = RequestUrl.from("/user/login");
        List<String> headerValues = Arrays.asList("POST /user/login HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=kjm&password=kjm");
        Request request = createPostRequest(url, headerValues);
        Response response = new Response();
        Controller controller = factory.mappingController(request);
        controller.processResponse(request, response);


        //then
        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.FOUND);
        assertThat(response.getResponseHeaders().getResponseHeaders().get("Location: ")).isEqualTo("http://localhost:8080/index.html");

        DataBase.deleteUser(user);
    }

    @Test
    @DisplayName("로그인 실패시 response 확인 테스트")
    void loginFailTest() throws IOException, URISyntaxException {

        RequestUrl url = RequestUrl.from("/user/login");
        List<String> headerValues = Arrays.asList("POST /user/login HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=kjm&password=kjm");
        Request request = createPostRequest(url, headerValues);
        Response response = new Response();
        Controller controller = factory.mappingController(request);
        controller.processResponse(request, response);

        //then
        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.FOUND);
        assertThat(response.getResponseHeaders().getResponseHeaders().get("Location: ")).isEqualTo("http://localhost:8080/user/login_failed.html");
    }

    @Test
    @DisplayName("로그인 후 유저목록 가져오기 테스트")
    void getUserListTest() throws IOException, URISyntaxException {

        // given
        User user1 = new User("easy", "easy", "easy", "easy@gmail.com");
        User user2 = new User("kjm", "kjm", "kjm", "kjm@gmail.com");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
        Session session = SessionRepository.getInstance().createSession();
        session.setAttribute("user", user2.getUserId());

        //when
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/user/list");
        Map<String, String> header = new HashMap<>();
        header.put("Request-Line:", "GET /user/list HTTP/1.1");
        header.put("Cookie:", "Session-Id="+session.getSessionId());

        Request request = new Request(method, url, new RequestInformation(header));
        Response response = new Response();

        Controller controller = factory.mappingController(request);
        controller.processResponse(request, response);

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

        Map<String, String> confirmMap = new LinkedHashMap<>();
        confirmMap.put("Content-Type: ", "text/html");
        confirmMap.put("Content-Length: ", "4914");


        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.OK);
        assertThat(response.getResponseHeaders().getResponseHeaders()).isEqualTo(confirmMap);
        assertThat(response.getResponseBody().getBody()).isEqualTo(confirmBody);
    }

    @Test
    @DisplayName("로그인없이 유저목록 가져오기 실패 테스트")
    void getUserListWithoutLoginTest() throws IOException, URISyntaxException {

        User user1 = new User("easy", "easy", "easy", "easy@gmail.com");
        User user2 = new User("kjm", "kjm", "kjm", "kjm@gmail.com");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
        Session session = SessionRepository.getInstance().createSession();
        session.setAttribute("user", user2.getUserId());

        //when
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/user/list");
        Map<String, String> header = new HashMap<>();
        header.put("Request-Line:", "GET /user/list HTTP/1.1");
        header.put("Cookie:", "Session-Id="+12345610);

        Request request = new Request(method, url, new RequestInformation(header));
        Response response = new Response();

        Controller controller = factory.mappingController(request);
        controller.processResponse(request, response);

        Map<String, String> confirmMap = new LinkedHashMap<>();
        confirmMap.put("Location: ", "http://localhost:8080/user/login.html");


        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.FOUND);
        assertThat(response.getResponseHeaders().getResponseHeaders()).isEqualTo(confirmMap);
    }
}
