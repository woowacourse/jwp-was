package webserver.httphandler;

import controller.exception.MethodNotAllowedException;
import fileloader.FileLoader;
import fileloader.exception.LoadFileFailedException;
import fileloader.exception.NoSuchFileException;
import http.common.ContentTypeMapper;
import http.request.HttpRequest;
import http.request.RequestMethod;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.StringUtils;
import webserver.httphandler.exception.ResourceNotFoundException;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class HttpResourceRequestHandler extends AbstractHttpRequestHandler {
    private final FileLoader fileLoader;

    public HttpResourceRequestHandler(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    @Override
    public boolean canHandle(String path) {
        String[] url = StringUtils.split(path, "/");
        return StringUtils.isNotBlank(url) &&
                url[url.length - 1].matches("^[^/:*?<>|\"\\\\]+[.][a-zA-Z0-9]+$");
    }

    /**
     * 정적 자원에 대한 HTTP 요청을 처리
     *
     * @param httpRequest  HTTP 요청을 나타내는 객체
     * @param httpResponse HTTP 응답을 나타내는 객체
     * @return {@code true} handle 성공, {@code false} 해당 자원이 없어 handle 실패
     * @throws LoadFileFailedException   파일 I/O에서 오류가 날 경우 발생
     * @throws ResourceNotFoundException 요청한 자원이 없는 경우 발생
     */
    @Override
    public void handleInternal(HttpRequest httpRequest, HttpResponse httpResponse) {
        checkRequestMethod(httpRequest.getMethod());

        String filePath = httpRequest.getPath();
        try {
            byte[] file = fileLoader.loadFile(filePath);

            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute(CONTENT_TYPE, ContentTypeMapper.getContentType(filePath));
            httpResponse.addHeaderAttribute(CONTENT_LENGTH, String.valueOf(file.length));
            httpResponse.setBody(file);
        } catch (NoSuchFileException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    private void checkRequestMethod(RequestMethod method) {
        if (!RequestMethod.GET.equals(method)) {
            throw new MethodNotAllowedException();
        }
    }
}
