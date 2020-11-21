package webserver.http;

import webserver.ClientException;
import webserver.http.response.StatusCode;

public class IncorrectProtocolException extends ClientException {
    public IncorrectProtocolException(String identifier) {
        super(String.format("%s는 올바르지 않은 프로토콜입니다.", identifier));
    }

    @Override
    public StatusCode getStatus() {
        return StatusCode.BAD_REQUEST;
    }
}
