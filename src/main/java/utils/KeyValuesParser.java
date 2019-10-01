package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyValuesParser {
    public static Map<String, String> parse(String queryString, String keyValuesSplitter, String keyValueSplitter) {
        List<String> parameterLines = Arrays.asList(queryString.split(keyValuesSplitter));

        return parameterLines.stream()
                .map(parameterLine -> parameterLine.split(keyValueSplitter))
                .filter(splittedParameter -> 2 <= splittedParameter.length)
                .collect(Collectors.toMap(
                        splittedParameter -> splittedParameter[0],
                        splittedParameter -> splittedParameter[1]));
    }
}
