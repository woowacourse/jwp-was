package http.request;

public interface Request {
    RequestPath getRequestPath();

    RequestHeader getRequestHeader();

    RequestMethod getRequestMethod();
}
