package webserver.domain;

public class Response302Header extends ResponseHeader {

    public Response302Header() {
        super(HttpStatus.FOUND);
    }
}
