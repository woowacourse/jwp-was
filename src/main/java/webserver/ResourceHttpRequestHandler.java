package webserver;

import http.common.ContentTypeMapper;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.FileIoUtils;
import utils.exception.LoadFileFailedException;

public class ResourceHttpRequestHandler {
    private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";

    private static class StaticResourceHttpRequestHandlerLazyHolder {
        private static final ResourceHttpRequestHandler INSTANCE = new ResourceHttpRequestHandler();
    }

    /**
     * 정적 자원에 대한 HTTP 요청을 처리
     *
     * @param httpRequest
     * @param httpResponse
     * @return {@code true} handle 성공, {@code false} 해당 자원이 없어 handle 실패
     * @throws LoadFileFailedException 파일 IO에서 오류가 날 경우 발생.
     *                                 파일이 존재하지 않을 경우는 NullPointerException을 catch하여 return false
     */
    public boolean handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        String filePath = STATIC_RESOURCE_PATH_PREFIX + httpRequest.getPath();
        try {
            byte[] file = FileIoUtils.loadFileFromClasspath(filePath);

            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute("Content-Type", ContentTypeMapper.getContentType(filePath));
            httpResponse.addHeaderAttribute("Content-Length", String.valueOf(file.length));
            httpResponse.setBody(file);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static ResourceHttpRequestHandler getInstance() {
        return StaticResourceHttpRequestHandlerLazyHolder.INSTANCE;
    }
}
