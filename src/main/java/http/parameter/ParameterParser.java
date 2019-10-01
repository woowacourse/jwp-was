package http.parameter;

import utils.KeyValuesParser;

import java.util.Map;

public class ParameterParser {
    private static final String PARAMETER_LINE_SPLITTER = "&";
    private static final String PARAMETER_SPLITTER = "=";

    public static Map<String, String> parse(String queryString) {
        return KeyValuesParser.parse(queryString, PARAMETER_LINE_SPLITTER, PARAMETER_SPLITTER);
    }
}
