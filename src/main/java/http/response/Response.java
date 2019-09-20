package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Response {
    void doResponse(DataOutputStream dos) throws IOException, URISyntaxException;
}
