package webserver.request.body;

public interface RequestBody {
    String get(String key);
    boolean isEmpty();
}
