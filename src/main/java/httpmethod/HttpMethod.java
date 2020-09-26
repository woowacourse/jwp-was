package httpmethod;

public enum HttpMethod {
    POST(new Post()),
    GET(new Get()),
    PATCH(new Patch()),
    DELETE(new Delete());

    final Method method;

    HttpMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
