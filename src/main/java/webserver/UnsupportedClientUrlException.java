package webserver;

import webserver.http.response.StatusCode;

public class UnsupportedClientUrlException extends ClientException {
    public UnsupportedClientUrlException(String requestUrl) {
        super(String.format("해당 URL에 대한 요청은 지원하지 않습니다. {'URL' : %s}", requestUrl));
    }

    @Override
    public StatusCode getStatus() {
        return StatusCode.NOT_FOUND;
    }
}
