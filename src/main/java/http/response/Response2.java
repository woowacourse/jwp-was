package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Response2 {
    void doResponse(DataOutputStream dos) throws IOException, URISyntaxException;
}
