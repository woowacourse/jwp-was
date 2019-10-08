package utils;

import utils.io.FileExtension;
import utils.io.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpResponse.HttpResponseBuilder;
import webserver.env.Env;
import webserver.env.Router;
import webserver.env.Session;
import webserver.httpelement.HttpContentType;
import webserver.httpelement.HttpLocation;
import webserver.httpelement.HttpSetCookie;
import webserver.httpelement.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class ControllerUtils {
    public static HttpResponse serveStaticFile(String filePath, HttpRequest req) {
        return FileIoUtils.loadFileFromClasspath(Router.STATIC_FILE_PATH + filePath).map(body ->
            HttpResponse.builder(
                    HttpContentType.fromFileExtension(new FileExtension(filePath))
            ).extractFieldsFromRequest(req).body(body).build()
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse redirectTo(String redirectPath, HttpRequest req) {
        return redirectResponseBuilder(redirectPath, req).build();
    }

    public static HttpResponse redirectTo(String redirectPath, HttpRequest req, List<HttpSetCookie> cookies) {
        final HttpResponseBuilder resBuilder = redirectResponseBuilder(redirectPath, req);
        cookies.forEach(resBuilder::addCookie);
        return resBuilder.build();
    }

    public static HttpResponse redirectTo(String redirectPath, HttpRequest req, HttpSetCookie cookie) {
        return redirectTo(redirectPath, req, Arrays.asList(cookie));
    }

    private static HttpResponseBuilder redirectResponseBuilder(String redirectPath, HttpRequest req) {
        return HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                            .extractFieldsFromRequest(req)
                            .status(HttpStatus.FOUND)
                            .location(new HttpLocation(redirectPath));
    }

    public static Optional<Session> ifLoggedIn(HttpRequest req) {
        return Optional.ofNullable(
                Env.sessionStorage().retrieve(req.getCookieOf(Session.COOKIE_IDENTIFIER))
        );
    }
}