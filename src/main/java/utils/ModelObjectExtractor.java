package utils;

public interface ModelObjectExtractor {
    <T> T extract(String body);
}
