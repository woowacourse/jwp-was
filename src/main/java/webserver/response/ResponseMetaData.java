package webserver.response;

public interface ResponseMetaData {

    HttpStatus getHttpStatus();

    String getVersion();

    String getResponseLine();

    boolean hasBody();

    byte[] getBody();

    String getHttpResponseHeaderFields();
}
