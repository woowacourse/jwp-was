package http.response;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private String name;
    private String value;
    private Map<String, String> optionValue;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        optionValue = new HashMap<>();
    }

    public void addOption(String optionName, String value) {
        optionValue.put(optionName, value);
    }

    public String getCookie() {
        StringBuilder cookie = new StringBuilder(name + "=" + value);
        for (String option : optionValue.keySet()) {
            cookie.append("; ").append(option).append("=").append(optionValue.get(option));
        }
        return cookie.toString();
    }
}
