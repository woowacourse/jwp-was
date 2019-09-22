package http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {
    public static RequestInformation parse(BufferedReader br) throws IOException {
        Map<String, String> requestInformation = new HashMap<>();

        String line = br.readLine();
        requestInformation.put("Request-Line:", line);

        putKeyValues(br, requestInformation, line);
        putParametersIfPresent(br, requestInformation);

        return new RequestInformation(requestInformation);
    }

    private static void putKeyValues(BufferedReader br, Map<String, String> requestInformation, String line) throws IOException {
        while (!"".equals(line)) {
            line = br.readLine();
            put(requestInformation, line);
        }
    }

    private static void put(Map<String, String> requestInformation, String line) {
        if (!"".equals(line)) {
            String[] tokens = line.split(" ");
            requestInformation.put(tokens[0], tokens[1]);
        }
    }

    private static void putParametersIfPresent(BufferedReader br, Map<String, String> requestInformation) throws IOException {
        String requestLine = requestInformation.get("Request-Line:");
        String[] tokens = requestLine.split(" ");
        String path = tokens[1];
        if (path.contains("//?")) {
            tokens = path.split("//?");
            String query = tokens[1];
            requestInformation.put("Query-Parameters:", query);
        }
        if (search(requestInformation, "Content-Length:") && requestInformation.get("Query-Parameters") != null) {
            String bodyContents = IOUtils.readData(br, Integer.parseInt(requestInformation.get("Content-Length:")));
            String queryParameters = requestInformation.get("Query-Parameters");
            String newQueryParameters = bodyContents + queryParameters;
            requestInformation.put("Query-Parameters:", newQueryParameters);
        }

        if (search(requestInformation, "Content-Length:") && requestInformation.get("Query-Parameters") == null) {
            String bodyContents = IOUtils.readData(br, Integer.parseInt(requestInformation.get("Content-Length:")));
            requestInformation.put("Query-Parameters:", bodyContents);
        }
    }

    private static boolean search(Map<String, String> requestLines, String key) {
        return requestLines.get(key) != null;
    }
}
