package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParameterParser {
    private static final String PARAMETER_LINE_SPLITTER = "&";
    private static final String PARAMETER_SPLITTER = "=";

    public static Map<String, String> parse(String queryString) {
        List<String> parameterLines = Arrays.asList(queryString.split(PARAMETER_LINE_SPLITTER));

        return parameterLines.stream().collect(Collectors.toMap(
                parameter -> parameter.split(PARAMETER_SPLITTER)[0],
                parameter -> parameter.split(PARAMETER_SPLITTER)[1]));
    }
}
