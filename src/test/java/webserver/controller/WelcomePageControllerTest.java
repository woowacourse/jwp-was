//package webserver.controller;
//
//import org.junit.jupiter.api.Test;
//import support.TextLoader;
//import webserver.http.HttpRequest;
//import webserver.http.HttpResponse;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class WelcomePageControllerTest {
//
//    @Test
//    void GET_정상처리() throws IOException {
//        InputStream load = TextLoader.load("Http_GET_index.txt");
//        HttpRequest httpRequest = HttpRequest.create(load);
//        HttpResponse httpResponse = new HttpResponse();
//
//        String view = WelcomePageController.getInstance().service(httpRequest, httpResponse);
//        assertThat(view).isEqualTo("/index.html");
//    }
//}