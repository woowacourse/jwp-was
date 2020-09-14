package utils;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ValueExtractor {
    private ValueExtractor() {
    }

    public static Map<String, List<String>> extract(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return new HashMap<>();
        }

        return Arrays.stream(value.split("&"))
            .map(param -> param.split("="))
            .collect(groupingBy(param -> param[0], mapping(param -> param[1], toList())));
    }
}
