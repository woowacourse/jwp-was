package http.request.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface ParameterParser {

    boolean isParseable(Map<String, String> requestInformation);

    void parse(BufferedReader br, Map<String, String> requestInformation) throws IOException;
}
