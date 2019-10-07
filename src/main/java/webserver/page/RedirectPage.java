package webserver.page;

import http.ContentType;

public class RedirectPage implements Page {
    private final String location;

    private RedirectPage(String location) {
        this.location = location;
    }

    public static RedirectPage location(String location) {
        return new RedirectPage(location);
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public byte[] getBody() {
        return new byte[0];
    }

    @Override
    public boolean isRedirectPage() {
        return true;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
