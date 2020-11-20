package webserver;

public interface RequestBody {
    String get(String key);
    boolean isEmpty();
}
