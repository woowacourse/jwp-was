package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.domain.HttpStatus;
import webserver.domain.Request;
import webserver.domain.Response;
import webserver.domain.StaticFile;

import java.io.IOException;

class StaticFileServer {
    private static final String EMPTY = "";
    private static final String STATIC_PATH = "./static";
    private static final String TEMPLATES_PATH = "/templates";
    private static final String NOT_FOUND_PAGE_PATH = "/error/404_not_found";
    private static final String URL_END_SUFFIX = "/";
    private static final String HTML = ".html";
    private static final String INDEX = "index";
    private static final TemplateLoader LOADER = new ClassPathTemplateLoader(TEMPLATES_PATH, HTML);
    private static final Handlebars HANDLEBARS = new Handlebars(LOADER);

    private static Response INTERNAL_ERROR_PAGE;
    private static Response NOT_FOUND_PAGE;

    static {
        final String internalError =
                "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>500 Internal Error</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>500 Internal Error</h1>\n" +
                "<p>서버에서 내부 오류가 발생하였습니다.</p>\n" +
                "</body>\n" +
                "</html>";
        INTERNAL_ERROR_PAGE = new Response.Builder().body(internalError).httpStatus(HttpStatus.INTERNAL_ERROR).build();
        try {
            final Template error404page = HANDLEBARS.compile(NOT_FOUND_PAGE_PATH);
            NOT_FOUND_PAGE = new Response.Builder().body(error404page.text()).httpStatus(HttpStatus.NOT_FOUND).build();
        } catch (final IOException e) {
            NOT_FOUND_PAGE = INTERNAL_ERROR_PAGE;
        }
    }

    static Response makeResponse(final Request request) {
        try {
            return tryStaticFileRead(request);
        } catch (final IOException | IllegalArgumentException e) {
            return NOT_FOUND_PAGE;
        }
    }

    private static Response tryStaticFileRead(final Request request) throws IOException, IllegalArgumentException {
        try {
            final StaticFile file = new StaticFile(makeFilePath(request, STATIC_PATH));
            return new Response.Builder().body(file).build();
        } catch (final IOException | IllegalArgumentException e) {
            final Template template = HANDLEBARS.compile(makeFilePath(request, EMPTY));
            return new Response.Builder().body(template.text()).build();
        }
    }

    private static String makeFilePath(final Request request, final String prefix) {
        final String requestPath = request.getPath();
        final String pathEnd = (requestPath.endsWith(URL_END_SUFFIX) || EMPTY.equals(requestPath)) ? INDEX : EMPTY;
        return prefix + requestPath + pathEnd;
    }
}
