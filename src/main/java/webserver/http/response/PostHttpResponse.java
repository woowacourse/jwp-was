package webserver.http.response;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PostHttpResponse extends HttpResponse {
    @Override
    public void handleResponse(DataOutputStream dos) {
        String body = httpRequests.getBody();
        Map<String, String> parameters = new HashMap<>();
        String[] urlParameters = body.split(URL_PARAMETER_REGEX);
        for (String parameter : urlParameters) {
            String[] entry = parameter.split(PARAMETER_REGEX);
            parameters.put(entry[0], entry[1]);
        }
        createUser(parameters);
    }
}
