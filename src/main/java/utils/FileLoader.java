package utils;

import org.slf4j.Logger;
import webserver.StaticFile;
import webserver.message.request.Request;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.slf4j.LoggerFactory.getLogger;

public class FileLoader {
    private static final Logger LOG = getLogger(FileLoader.class);

    private static final String STATIC_PATH = "./static";
    private static final String TEMPLATES_PATH = "./templates";
    private static final String NOT_FOUND_PAGE_PATH = TEMPLATES_PATH + "/error/404_not_found.html";

    public static Response serveStaticFile(final Request request) {
        return DataConverter.convertToResponse(loadFile(request));
    }

    private static StaticFile loadFile(final Request request) {
        StaticFile file;
        try {
            file = tryStaticFileRead(request);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            try {
                file = new StaticFile(NOT_FOUND_PAGE_PATH);
            } catch (IOException | URISyntaxException ex) {
                LOG.debug(ex.getMessage());
                file = null;
            }
        }
        return file;
    }

    private static StaticFile tryStaticFileRead(final Request request) throws IOException, URISyntaxException {
        try {
            return new StaticFile(makeFilePath(request, STATIC_PATH));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return new StaticFile(makeFilePath(request, TEMPLATES_PATH));
        }
    }

    private static String makeFilePath(final Request requestHeader, final String prefix) {
        final String requestPath = requestHeader.getPath();
        final String pathEnd = (requestPath.endsWith("/") || "".equals(requestPath)) ? "index.html" : "";
        return prefix + requestPath + pathEnd;
    }
}
