package http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class PostRequest extends AbstractRequest {
    public PostRequest(BufferedReader br, String[] tokens) throws IOException {
        super(br, tokens);
        parameters = new HashMap<>();
        String params = IOUtils.readData(br, Integer.parseInt(getRequestHeader().getHeaders().get("Content-Length")));
        extractParameter(params.split("&"));
    }

    private void extractParameter(String[] params) {
        Arrays.stream(params)
                .forEach(param -> {
                    String[] keyValues = param.split("=");
                    parameters.put(keyValues[0], keyValues[1]);
                });
    }
}
