package controller;

import utils.io.FileExtension;
import utils.io.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.httpelement.HttpContentType;
import webserver.httpelement.HttpStatusCode;
import webserver.router.Router;

public abstract class Controller {
    protected static HttpResponse serveStaticFile(String filePath, HttpRequest req) {
        return FileIoUtils.loadFileFromClasspath(Router.STATIC_FILE_PATH + filePath).map(body ->
            HttpResponse.builder(
                    HttpContentType.fromFileExtension(new FileExtension(filePath))
            ).extractFieldsFromRequest(req).body(body).build()
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    protected static HttpResponse redirectTo(String redirectPath, HttpRequest req) {
        return HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                            .extractFieldsFromRequest(req)
                            .statusCode(HttpStatusCode.FOUND)
                            .location(redirectPath)
                            .build();
    }
}