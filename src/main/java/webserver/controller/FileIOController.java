package webserver.controller;

import http.ContentType;
import http.ContentTypeFactory;
import http.ContentTypesSupplier;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class FileIOController extends AbstractController {
    private final String directoryPrefix;

    public FileIOController(String prefix) {
        this.directoryPrefix = prefix;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        final String filePath = request.getPath();
        byte[] body = FileIoUtils.loadFileFromClasspath(String.format("./%s%s", directoryPrefix, filePath));
        String accept = request.getHeader("Accept");

//        String extension = parseExtension(filePath);
//         ContentType contentType = ContentType.fromMimeType("text/html").get();
//        ContentType contentType = ContentType.fromExtentsion(extension);
//        accept.split(",")[0];


        // contentTypeFactory.createContentType(accept, contentTypesGeneratorSupportedByServer)
        ContentTypesSupplier contentTypesSupplierFromFileExtension = () -> {
            String extension = FileIoUtils.parseExtensionFromFilePath(filePath);

            return ContentType.fromExtension(extension)
                    .map(Arrays::asList)
                    .orElse(Collections.EMPTY_LIST);
        };
        // was 에서 익셉션과 사용자 코드에서 익셉션을 어떻게 분리해서 처리할까??
        // 이 부분을 생각하면... 어떤 부분까지 사용자 코드에서 적용시켜야할지가 보일 것 같다...
        //
        // 응답에 있는 정보를 채우는 부분 (+ 여기서 발생하는 익셉션)
        // 응답을 보내는 부분
        ContentType contentType = ContentTypeFactory.create(accept, contentTypesSupplierFromFileExtension);

        response.setHeader("Content-Type", contentType.toHeaderValue());
        response.setHeader("Content-Length", Integer.toString(body.length));
        response.response200Header();
        response.responseBody(body);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        super.doPost(request, response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileIOController that = (FileIOController) o;
        return Objects.equals(directoryPrefix, that.directoryPrefix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directoryPrefix);
    }
}
