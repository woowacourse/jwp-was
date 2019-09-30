package webserver.view;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class FileView implements View {
    private final String filePath;

    public FileView(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        response.setContentLengthAndType(body.length, FileIoUtils.loadMIMEFromClasspath(filePath));
        response.ok(body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileView fileView = (FileView) o;

        return Objects.equals(filePath, fileView.filePath);
    }

    @Override
    public int hashCode() {
        return filePath != null ? filePath.hashCode() : 0;
    }
}
