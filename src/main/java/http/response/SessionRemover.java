package http.response;

@FunctionalInterface
public interface SessionRemover {
    void remove(String id);
}
