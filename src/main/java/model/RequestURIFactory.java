package model;

@FunctionalInterface
public interface RequestURIFactory {
    RequestURI create(String uri);
}
