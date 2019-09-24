package utils.parser.simple;

public interface KeyValueParser<T> {
     T interpret(String input);
}