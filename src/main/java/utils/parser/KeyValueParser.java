package utils.parser;

public interface KeyValueParser<T> {
     T interpret(String input);
}