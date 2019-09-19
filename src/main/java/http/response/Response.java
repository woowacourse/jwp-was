package http.response;

import java.io.DataOutputStream;

public interface Response {
    void doResponse(DataOutputStream dos);
}
