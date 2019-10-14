package webserver.file;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class File {
    private static final String PATH_DELIMITER = "/";
    private static final String EXTENSION_DELIMITER = "\\.";

    private final String path;
    private final String name;
    private final String extension;
    private final byte[] body;

    File(final String prefix, final String path) throws IOException, URISyntaxException {
        this.path = makeFilePath(prefix, path);
        this.name = extractName(this.path);
        this.extension = extractExtension(this.name);
        this.body = FileIoUtils.loadFileFromClasspath(this.path);
    }

    private String extractName(final String path) {
        final String[] pathFragments = path.split(PATH_DELIMITER);
        return pathFragments[pathFragments.length - 1];
    }

    private String extractExtension(final String fileName) {
        final String[] namePieces = fileName.split(EXTENSION_DELIMITER);
        return namePieces.length == 1 ? "" : namePieces[namePieces.length - 1];
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public byte[] getBody() {
        return body;
    }

    public int getSize() {
        return body.length;
    }

    static String makeFilePath(final String prefix, final String path) {
        final String pathEnd = (path.endsWith("/") || "".equals(path)) ? "index.html" : "";
        return prefix + path + pathEnd;
    }

}
