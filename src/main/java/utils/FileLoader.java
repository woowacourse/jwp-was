package utils;

import org.slf4j.Logger;
import utils.exception.InvalidFileAccessException;
import webserver.file.DynamicFile;
import webserver.file.File;
import webserver.file.StaticFile;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.slf4j.LoggerFactory.getLogger;

public class FileLoader {
    private static final Logger LOG = getLogger(FileLoader.class);

    private static final String NOT_FOUND_PATH = "/error/404_not_found.html";
    private static final String INTERNAL_SERVER_ERROR_PATH = "/error/500_internal_error.html";
    private static final String NOT_FOUND_FILE_MESSAGE = "해당 파일이 존재하지 않습니다.";

    public static File loadFile(final String path) throws IOException, URISyntaxException, InvalidFileAccessException {
        if (StaticFile.supports(path)) {
            return new StaticFile(path);
        }
        if (DynamicFile.supports(path)) {
            return new DynamicFile(path);
        }

        throw new InvalidFileAccessException();
    }

    public static File loadNotFoundFile() {
        try {
            return new DynamicFile(NOT_FOUND_PATH);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.debug("loadNotFoundFile {}", e.getMessage());
            throw new InvalidFileAccessException(NOT_FOUND_FILE_MESSAGE);
        }
    }

    public static File loadInternalServerErrorFile() {
        try {
            return new DynamicFile(INTERNAL_SERVER_ERROR_PATH);
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
