package http.request;

import java.io.BufferedReader;
import java.util.Map;

public class UrlParameterParser implements ParameterParser {
    @Override
    public boolean isParseable(Map<String, String> requestInformation) {
        String requestLine = requestInformation.get("Request-Line:");
        String[] tokens = requestLine.split(" ");
        String path = tokens[1];

        return path.contains("//?");
    }

    @Override
    public void parse(BufferedReader br, Map<String, String> requestInformation) {
        String requestLine = requestInformation.get("Request-Line:");
        String[] tokens = requestLine.split(" ");
        String path = tokens[1];

        tokens = path.split("//?");
        String query = tokens[1];
        requestInformation.put("Query-Parameters:", query);
    }
}
