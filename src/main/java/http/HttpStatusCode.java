package http;

public enum HttpStatusCode {
    OK(200, "OK"),
    FOUND(302, "FOUND"),
    Method_Not_Allowed(405, "Method Not Allowed"),
    Not_Found(404, "Not Found");


    private int code;
    private String message;

    HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String findCodeAndMessage() {
        return code + " " + message;
    }
}
