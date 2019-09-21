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
        putBodyIfPresent(br, requestInformation);

        return new RequestInformation(requestInformation);
    }

    private static void putKeyValues(BufferedReader br, Map<String, String> requestInformation, String line) throws IOException {
        while(!"".equals(line)) {
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

    private static void putBodyIfPresent(BufferedReader br, Map<String, String> requestInformation) throws IOException {
        if (search(requestInformation, "Content-Length:")) {
            String bodyContents = IOUtils.readData(br, Integer.parseInt(requestInformation.get("Content-Length:")));
            requestInformation.put("Body-Contents:", bodyContents);
        }
    }

    private static boolean search(Map<String, String> requestLines, String key) {
        return requestLines.get(key) != null;
    }
}
