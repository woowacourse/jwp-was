package webserver;

import fileloader.FileLoader;
import fileloader.NonExistentFileException;
import fileloader.exception.LoadFileFailedException;
import http.common.ContentTypeMapper;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;

public class ResourceHttpRequestHandler {
    private final FileLoader fileLoader;

    public ResourceHttpRequestHandler(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    /**
     * 정적 자원에 대한 HTTP 요청을 처리
     *
     * @param httpRequest  HTTP 요청을 나타내는 객체
     * @param httpResponse HTTP 응답을 나타내는 객체
     * @return {@code true} handle 성공, {@code false} 해당 자원이 없어 handle 실패
     * @throws LoadFileFailedException 파일 IO에서 오류가 날 경우 발생.
     *                                 파일이 존재하지 않을 경우는 NullPointerException을 catch하여 return false
     */
    public boolean handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        String filePath = httpRequest.getPath();
        try {
            byte[] file = fileLoader.loadFile(filePath);

            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute("Content-Type", ContentTypeMapper.getContentType(filePath));
            httpResponse.addHeaderAttribute("Content-Length", String.valueOf(file.length));
            httpResponse.setBody(file);
            return true;
        } catch (NonExistentFileException e) {
            return false;
        }
    }
}
