package web.view;

import utils.ContentTypeMatcher;
import utils.FileIoUtils;
import web.HeaderName;
import web.model.Model;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.response.HttpStatusCode;

import java.io.IOException;

public class FileView extends AbstractView {

    public FileView(String filePath) {
        super(filePath);
    }

    @Override
    public void processRender(Model model, HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestPath = httpRequest.getRequestPath();

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(super.path);
            httpResponse.putHeader(HeaderName.CONTENT_TYPE, ContentTypeMatcher.findResourceContentType(requestPath));
            httpResponse.response(HttpStatusCode.OK, body);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("%s : 해당경로에 파일이 존재하지 않습니다.", requestPath));
        }
    }
}
