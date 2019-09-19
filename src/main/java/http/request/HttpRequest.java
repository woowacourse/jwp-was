package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private static Map<RequestMethod, RequestCreator> requestCreators = new HashMap<>();

    static {
        requestCreators.put(RequestMethod.GET, new GetRequestCreator());
        requestCreators.put(RequestMethod.POST, new PostRequestCreator());
    }

    public static Request getRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] tokens = getTokens(line);

        RequestCreator requestCreator = requestCreators.get(RequestMethod.from(tokens[0]));
        return requestCreator.create(br, tokens);
    }

    private static String[] getTokens(String line) {
        return line.split(" ");
    }
}

class GetRequestCreator implements RequestCreator {
    @Override
    public Request create(BufferedReader br, String[] tokens) throws IOException {
        return new GetRequest(br, tokens);
    }
}

class PostRequestCreator implements RequestCreator {
    @Override
    public Request create(BufferedReader br, String[] tokens) throws IOException {
        return new PostRequest(br, tokens);
    }
}

