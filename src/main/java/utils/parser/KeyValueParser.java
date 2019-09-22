package utils.parser;

import java.util.Map;

public interface KeyValueParser<T> {
     T interpret(String input);
}