package web.request;

import exception.InvalidRequestBodyException;

import java.util.Objects;

public class RequestBody extends ParameterMapper {
    public RequestBody() {
        super();
    }

    public RequestBody(String requestBody) {
        validateBody(requestBody);
        mappingParameters(requestBody);
    }

    private void validateBody(String requestBody) {
        if (Objects.isNull(requestBody) || requestBody.isEmpty()) {
            throw new InvalidRequestBodyException();
        }
    }
}
