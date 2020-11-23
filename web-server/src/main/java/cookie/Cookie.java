package cookie;

public class Cookie {

    private String name;
    private String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @param cookieFormat : <cookie-name>=<cookie-value>
     */
    public static Cookie from(String cookieFormat) {
        String[] split = cookieFormat.split("=");

        if (split.length != 2) {
            throw new IllegalArgumentException("Cookie Format is wrong.");
        }
        return new Cookie(split[0], split[1]);
    }

    /**
     * @return "<cookie-name>=<cookie-value>"
     */
    public String toFormat() {
        return name + "=" + value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
