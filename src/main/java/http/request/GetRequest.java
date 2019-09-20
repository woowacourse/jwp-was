package http.request;

import java.util.List;

public class GetRequest extends AbstractRequest {
    GetRequest(List<String> lines, String[] tokens) {
        super(lines, tokens);
    }
}
