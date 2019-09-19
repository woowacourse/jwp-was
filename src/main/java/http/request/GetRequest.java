package http.request;

import java.io.BufferedReader;
import java.io.IOException;

public class GetRequest extends AbstractRequest {
    public GetRequest(BufferedReader br, String[] tokens) throws IOException {
        super(br, tokens);
    }
}
