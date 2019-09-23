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
    private static final String NOT_FOUND_PATH = TEMPLATES_PATH + "/error/404_not_found.html";
    private static final String INTERNAL_SERVER_ERROR_PATH = TEMPLATES_PATH + "/error/500_internal_error.html";

    public static StaticFile loadStaticFile(final Request request) throws IOException, URISyntaxException, NullPointerException {
        try {
            return new StaticFile(makeFilePath(request, STATIC_PATH));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return new StaticFile(makeFilePath(request, TEMPLATES_PATH));
        }
    }

    public static StaticFile loadNotFoundFile() {
        try {
            return new StaticFile(NOT_FOUND_PATH);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.debug("loadNotFoundFile {}", e.getMessage());
            return null; // TODO 여기를 어떻게 처리해야 할까
        }
    }

    public static StaticFile loadInternalServerErrorFile() {
        try {
            return new StaticFile(INTERNAL_SERVER_ERROR_PATH);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.debug("loadInternalServerErrorFile {}", e.getMessage());
            return null; // TODO 여기를 어떻게 처리해야 할까
        }
    }

    private static String makeFilePath(final Request requestHeader, final String prefix) {
        final String requestPath = requestHeader.getPath();
        final String pathEnd = (requestPath.endsWith("/") || "".equals(requestPath)) ? "index.html" : "";
        return prefix + requestPath + pathEnd;
    }

}
