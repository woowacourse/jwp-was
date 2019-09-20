package http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PostRequest extends AbstractRequest {

    PostRequest(List<String> lines, String[] tokens, BufferedReader br) throws IOException {
        super(lines, tokens);
        parameters = new HashMap<>();
        String params = IOUtils.readData(br, Integer.parseInt(getRequestHeader().getHeaders().get("Content-Length")));
        extractParameter(params.split("&"));
    }
}
