package http.request;

import java.util.HashMap;
import java.util.List;

public class GetRequest extends AbstractRequest {
    GetRequest(List<String> lines, String[] tokens) {
        super(lines, tokens);
        parameters = new HashMap<>();

        checkGetParameter();
    }

    private void checkGetParameter() {
        if (requestPath.getPath().contains("?")) {
            String[] params = requestPath.getPath().split("\\?");
            extractParameter(params[1].split("&"));
        }
    }
}
