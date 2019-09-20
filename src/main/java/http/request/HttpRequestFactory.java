package http.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestFactory {
    private static final Map<RequestMethod, RequestCreator> requestCreators = new HashMap<>();

    static {
        requestCreators.put(RequestMethod.GET, new GetRequestCreator());
        requestCreators.put(RequestMethod.POST, new PostRequestCreator());
    }

    public static Request getRequest(String firstLine, List<String> lines) throws IOException {
        String[] tokens = getTokens(firstLine);

        RequestCreator requestCreator = requestCreators.get(RequestMethod.from(tokens[0]));
        return requestCreator.create(lines, tokens);
    }

    private static String[] getTokens(String line) {
        return line.split(" ");
    }

    static class GetRequestCreator implements RequestCreator {
        @Override
        public Request create(List<String> lines, String[] tokens) {
            return new GetRequest(lines, tokens);
        }
    }

    static class PostRequestCreator implements RequestCreator {
        @Override
        public Request create(List<String> lines, String[] tokens) {
            return new PostRequest(lines, tokens);
        }
    }
}



