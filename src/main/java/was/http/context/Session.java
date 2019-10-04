package was.http.context;

public interface Session {
    void add(String key, Object value);
    Object get(String key);
    void clear();
}
