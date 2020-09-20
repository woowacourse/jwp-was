package view;

public class View {

    public static final String REDIRECT_PREFIX = "redirect:";

    private String uri;

    public View(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public boolean isRedirectView() {
        return uri.startsWith(REDIRECT_PREFIX);
    }
}
