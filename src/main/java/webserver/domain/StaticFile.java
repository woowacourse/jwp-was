package webserver.domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticFile {
    private static final String URL_DELIMITER = "/";
    private static final String EXTENSION_DELIMITER = "\\.";
    private static final String EMPTY = "";

    private final String path;
    private final String name;
    private final String extension;
    private final MediaType mediaType;
    private final byte[] body;

    public StaticFile(final String path) throws IOException, IllegalArgumentException {
        this.path = path;
        this.name = extractName(path);
        this.extension = extractExtension(this.name);
        this.mediaType = MediaType.of(this.extension);
        this.body = loadFileFromPath(this.path);
    }

    private String extractName(final String path) {
        final String[] pathFragments = path.split(URL_DELIMITER);
        return pathFragments[pathFragments.length - 1];
    }

    private String extractExtension(final String fileName) {
        final String[] namePieces = fileName.split(EXTENSION_DELIMITER);
        return namePieces.length == 1 ? EMPTY : namePieces[namePieces.length - 1];
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

    public MediaType getMediaType() {
        return mediaType;
    }

    public byte[] getBody() {
        return body;
    }

    public int getSize() {
        return body.length;
    }

    private static byte[] loadFileFromPath(final String filePath) throws IOException {
        try {
            final URL url = StaticFile.class.getClassLoader().getResource(filePath);
            return Files.readAllBytes(Paths.get(url.toURI()));
        } catch (final URISyntaxException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }
}
