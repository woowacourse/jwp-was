package webserver.http.body;

import webserver.http.NameAndValuePair;

public interface HttpBody extends NameAndValuePair {
    String getValue(String key);
}