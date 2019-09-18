package webserver;

public class URL {
    private final String path;
    private final String queryString;

    public URL(String path, String queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static URL of(String url) {
        int index = getIndex(url);
        String path = url.substring(0, index);
        String queryString = getQueryString(url, index + 1);
        return new URL(path, queryString);
    }

    private static String getQueryString(String url, int index) {
        return url.length() >= index ? url.substring(index) : "";
    }

    private static int getIndex(String url) {
        int index = url.indexOf("?");
        return index > 0 ? index : url.length();
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}
