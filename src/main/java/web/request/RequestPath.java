package web.request;

import exception.InvalidRequestPathException;

import java.util.Objects;

public class RequestPath extends ParameterMapper {
    private final String target;

    public RequestPath(String path) {
        validatePath(path);
        target = path.split("\\?")[0];
        if (path.contains("?")) {
            mappingParameters(path.split("\\?")[1]);
        }
    }

    private void validatePath(String path) {
        if (Objects.isNull(path) || path.isEmpty()) {
            throw new InvalidRequestPathException();
        }
    }

    public String getTarget() {
        return target;
    }
}
