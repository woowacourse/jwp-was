package utils;

import org.slf4j.Logger;
import utils.exception.InvalidFileAccessException;
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
    private static final String NOT_FOUND_FILE_MESSAGE = "해당 파일이 존재하지 않습니다.";

    public static StaticFile loadStaticFile(final String path) {
        try {
            return new StaticFile(makeFilePath(path, STATIC_PATH));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            try {
                return new StaticFile(makeFilePath(path, TEMPLATES_PATH));
            } catch (IOException | URISyntaxException | NullPointerException ex) {
                throw new InvalidFileAccessException();
            }
        }
    }

    public static StaticFile loadNotFoundFile() {
        try {
            return new StaticFile(NOT_FOUND_PATH);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.debug("loadNotFoundFile {}", e.getMessage());
            throw new InvalidFileAccessException(NOT_FOUND_FILE_MESSAGE);
        }
    }

    public static StaticFile loadInternalServerErrorFile() {
        try {
            return new StaticFile(INTERNAL_SERVER_ERROR_PATH);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.debug("loadInternalServerErrorFile {}", e.getMessage());
            throw new InvalidFileAccessException(NOT_FOUND_FILE_MESSAGE);
        }
    }

    private static String makeFilePath(final String path, final String prefix) {
        final String pathEnd = (path.endsWith("/") || "".equals(path)) ? "index.html" : "";
        return prefix + path + pathEnd;
    }

}
