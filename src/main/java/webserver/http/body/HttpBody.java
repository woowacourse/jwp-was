package webserver.http.body;

public interface HttpBody extends NameAndValuePair {
    String getValue(String key);

    static HttpBody empty() {
        return new HttpBody() {
            @Override
            public String getValue(String key) {
                return null;
            }

            @Override
            public String toHttpMessage() {
                return "";
            }
        };
    }

    String toHttpMessage();
}