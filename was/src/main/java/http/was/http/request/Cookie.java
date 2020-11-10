package http.was.http.request;

import org.springframework.util.Assert;

public class Cookie {

    private final String name;

    private final String value;

    public Cookie(String name, String value) {
        Assert.hasLength(name, "'name' is required and must not be empty.");
        this.name = name;
        this.value = validateByValue(value);
    }

    private String validateByValue(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.name + "=" + this.value;
    }
}
