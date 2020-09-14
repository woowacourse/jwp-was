package webserver.http.body;

public interface HttpBody extends NameAndValuePair {
    String getValue(String key);
}