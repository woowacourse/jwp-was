package webserver.page;

import http.ContentType;
import utils.FileIoUtils;

public class FilePage implements Page {
    private final ContentType contentType;
    private final byte[] body;

    private FilePage(ContentType contentType, byte[] body) {
        this.contentType = contentType;
        this.body = body;
    }

    public static FilePage fromPath(String filePath) {
        String extension = FileIoUtils.parseExtensionFromFilePath(filePath);

        ContentType contentType = ContentType.fromExtension(extension).orElse(ContentType.OCTET_STREAM);
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);

        return new FilePage(contentType, body);
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public byte[] getBody() {
        return body;
    }
}
