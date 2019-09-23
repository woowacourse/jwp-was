package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ResourceRequestResolver {
    private String TEMPLATE_PATH = "./template";
    private String STATIC_PATH = "./static";

    private List<ResourceResolverRegistration> resourceResolverRegistrations = new ArrayList<>();

//파일 가져오는 기능(얘가 스테틱인지 템플릿인지는 알 필요X)
    public void resolve(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        try {
            byte[] body;


            body = FileIoUtils.loadFileFromClasspath(String.format("./templates%s", httpRequest.getPath()));
            String[] splitPath = httpRequest.getPath().split("\\.");
            String contentType = splitPath[splitPath.length - 1];
            // html 찾기;
            if (body != null) {
                httpResponse.okResponse(contentType, body);
            }

            //css,js 찾기
            body = FileIoUtils.loadFileFromClasspath(String.format("./static%s", httpRequest.getPath()));
            if (body == null) {
                throw new NotFoundException();
            }
            httpResponse.okResponse(contentType, body);
        } catch (NotFoundException e) {
            httpResponse.notFound();
        }
    }
}
