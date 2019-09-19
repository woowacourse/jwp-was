package http.request;

import java.io.BufferedReader;
import java.io.IOException;

public class PostRequest extends AbstractRequest {
    public PostRequest(BufferedReader br, String[] tokens) throws IOException {
        super(br, tokens);
    }
}
