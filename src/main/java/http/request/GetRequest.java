package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class GetRequest extends AbstractRequest {
    GetRequest(BufferedReader br, String[] tokens) throws IOException {
        super(br, tokens);
        parameters = new HashMap<>();

        if (requestPath.getPath().contains("?")) {
            String[] parameters = requestPath.getPath().split("\\?");
            extractParameter(parameters[1].split("&"));
        }
    }

    private void extractParameter(String[] params) {
        Arrays.stream(params)
                .forEach(param -> {
                    String[] keyValues = param.split("=");
                    parameters.put(keyValues[0], keyValues[1]);
                });
    }
}
