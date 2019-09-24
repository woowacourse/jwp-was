package http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class BodyParameterParser implements ParameterParser {
    @Override
    public boolean isParseable(Map<String, String> requestInformation) {
        return (requestInformation.get("Content-Length:") != null && requestInformation.get("Query-Parameters:") == null);
    }

    @Override
    public void parse(BufferedReader br, Map<String, String> requestInformation) throws IOException {
        String bodyContents = IOUtils.readData(br, Integer.parseInt(requestInformation.get("Content-Length:")));
        requestInformation.put("Query-Parameters:", bodyContents);
        System.out.println(requestInformation.get("Query-Parameters:"));
    }
}
