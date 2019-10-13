package utils;

import org.slf4j.Logger;
import utils.exception.NoSuchFileException;
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

    public static File loadFile(final String path) throws IOException, URISyntaxException, NoSuchFileException {
        if (StaticFile.supports(path)) {
            return new StaticFile(path);
        }
        if (DynamicFile.supports(path)) {
            return new DynamicFile(path);
        }

        throw new NoSuchFileException();
    }

    public static File loadNotFoundFile() {
        try {
            return new DynamicFile(NOT_FOUND_PATH);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.debug("loadNotFoundFile {}", e.getMessage());
            throw new NoSuchFileException();
        }
    }

    public static File loadInternalServerErrorFile() {
        try {
            return new DynamicFile(INTERNAL_SERVER_ERROR_PATH);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.debug("loadInternalServerErrorFile {}", e.getMessage());
            throw new NoSuchFileException();
        }
    }

}
