package http.common;

import java.util.List;
import java.util.Map;

public final class CookieBuilder {

    private Map<String, String> attributeWithValue;
    private List<String> attributeWithoutValue;

    private CookieBuilder() {
    }

    public static CookieBuilder create() {
        return new CookieBuilder();
    }

    public CookieBuilder withAttributeWithValue(Map<String, String> attributeWithValue) {
        this.attributeWithValue = attributeWithValue;
        return this;
    }

    public CookieBuilder withAttributeWithoutValue(List<String> attributeWithoutValue) {
        this.attributeWithoutValue = attributeWithoutValue;
        return this;
    }

    public Cookie build() {
        return new Cookie(attributeWithValue, attributeWithoutValue);
    }
}
