package webserver.response;

public enum StatusCode {
    OK(200), FOUND(302), BAD_REQUEST(400), NOT_FOUND(404), METHOD_NOT_ALLOWED(405), INTERNAL_SERVER_ERROR(500);

    private final int statusCode;

    StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static StatusCode of(View view) {
        if (view.isRedirect()) {
            return FOUND;
        }
        return OK;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
