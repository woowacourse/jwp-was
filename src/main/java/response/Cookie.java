package response;

class Cookie {

    private String name;
    private String value;

    Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    String toHttpResponseHeaderValueFormat() {
        // <cookie-name>=<cookie-value>
        return name + "=" + value;
    }

    String getName() {
        return name;
    }

    String getValue() {
        return value;
    }
}
