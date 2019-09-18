package webserver;

public class URL {
    private final String path;
    private final RequestParameter requestParameter;

    public URL(String path, RequestParameter requestParameter) {
        this.path = path;
        this.requestParameter = requestParameter;
    }

    public static URL of(String url) {
        int index = getIndex(url);
        String path = url.substring(0, index);
        String requestParameter = getReq(url, index + 1);
        return new URL(path, new RequestParameter(requestParameter));
    }

    private static String getReq(String url, int index) {
        return url.length() >= index ? url.substring(index) : "";
    }

    private static int getIndex(String url) {
        int index = url.indexOf("?");
        return index > 0 ? index : url.length();
    }

    public String getPath() {
        return path;
    }

    public RequestParameter getRequestParameter() {
        return requestParameter;
    }
}
