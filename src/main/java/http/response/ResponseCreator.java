package http.response;

public interface ResponseCreator {
    Response create(String path);
}
