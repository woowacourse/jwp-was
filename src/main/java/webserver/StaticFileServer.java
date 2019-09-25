package webserver;

import webserver.domain.HttpStatus;
import webserver.domain.Request;
import webserver.domain.Response;
import webserver.domain.StaticFile;

import java.io.IOException;

class StaticFileServer {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final String NOT_FOUND_PAGE_PATH = TEMPLATES_PATH + "/error/404_not_found.html";
    private static final String URL_END_SUFFIX = "/";
    private static final String INDEX_HTML = "index.html";
    private static final String EMPTY = "";

    private static StaticFile NOT_FOUND_PAGE_FILE;

    static {
        try {
            NOT_FOUND_PAGE_FILE = new StaticFile(NOT_FOUND_PAGE_PATH);
        } catch (IOException e) {
            NOT_FOUND_PAGE_FILE = null;
        }
    }

    static Response makeResponse(final Request request) {
        try {
            final StaticFile file = tryStaticFileRead(request);
            return new Response.Builder().body(file).build();
        } catch (final IOException | IllegalArgumentException e) {
            return new Response.Builder().body(NOT_FOUND_PAGE_FILE).httpStatus(HttpStatus.NOT_FOUND).build();
        }
    }

    private static StaticFile tryStaticFileRead(final Request request) throws IOException, IllegalArgumentException {
        try {
            return new StaticFile(makeFilePath(request, STATIC_PATH));
        } catch (final IOException | IllegalArgumentException e) {
            return new StaticFile(makeFilePath(request, TEMPLATES_PATH));
        }
    }

    private static String makeFilePath(final Request request, final String prefix) {
        final String requestPath = request.getPath();
        final String pathEnd = (requestPath.endsWith(URL_END_SUFFIX) || EMPTY.equals(requestPath)) ? INDEX_HTML : EMPTY;
        return prefix + requestPath + pathEnd;
    }
}
