package http.parameter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParameterParser {
    private static final String PARAMETER_LINE_SPLITTER = "&";
    private static final String PARAMETER_SPLITTER = "=";

    public static Map<String, String> parse(String queryString) {
        List<String> parameterLines = Arrays.asList(queryString.split(PARAMETER_LINE_SPLITTER));

        return parameterLines.stream()
                .map(parameterLine -> parameterLine.split(PARAMETER_SPLITTER))
                .filter(splittedParameter -> 2 <= splittedParameter.length)
                .collect(Collectors.toMap(
                        splittedParameter -> splittedParameter[0],
                        splittedParameter -> splittedParameter[1]));
    }
}
