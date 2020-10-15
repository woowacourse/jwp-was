package response;

class Cookie {

    private String name;
    private String value;
    private String path;

    Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    String toHttpResponseHeaderValueFormat() {
        // <cookie-name>=<cookie-value>; Path=<path-value>
        return name + "=" + value
            + "; Path=" + path;
    }

    String getName() {
        return name;
    }

    String getValue() {
        return value;
    }
}
