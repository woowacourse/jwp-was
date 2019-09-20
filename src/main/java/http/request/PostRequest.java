package http.request;

import java.util.List;

public class PostRequest extends AbstractRequest {

    PostRequest(List<String> lines, String[] tokens) {
        super(lines, tokens);
    }
}
