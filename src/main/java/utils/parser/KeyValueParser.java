package utils.parser;

import java.util.Map;

public interface KeyValueParser<T> {
    Map<String, String> toMap(String x);
    //T parse(String x);
    //String serialize(Map<String, String> x);
    //String serialize(T x);
}