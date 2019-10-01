package webserver.controller;

import http.Session;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        log.debug("called..!!");

        // to test session.invalidate();
        // suppose only loggedIn user can access here
        Session session = request.getSession(false).get();
        session.invalidate();

        // to index page
        String redirectLocation = "/index.html";
        response.setHeader("Location", redirectLocation);
        response.response302Header();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        log.debug("called..!!");
    }
}
