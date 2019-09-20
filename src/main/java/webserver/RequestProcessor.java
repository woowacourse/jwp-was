package webserver;

import model.UserService;
import webserver.support.Request;
import webserver.support.RequestHeader;
import webserver.support.ResponseHeader;

public class RequestProcessor {
    private static final String REGEX_FOR_FILE = "/?[A-Za-z0-9/.\\-]+\\.(html|ico|css|js)";

    public ResponseHeader process(Request request) {
        String url = request.extractUrl();

        if (url.matches(REGEX_FOR_FILE)) {
            return new ResponseHeader(200, url.substring(url.lastIndexOf(".") + 1), null, url);
        } else {
            if (request.get(RequestHeader.HTTP_METHOD).equals("POST")) {
                String location = new UserService().addUser(request.extractFormData());
                return new ResponseHeader(302, location.substring(location.lastIndexOf(".") + 1), location, url);
            }
            String location = new UserService().addUser(request.extractQueryParameter());
            return new ResponseHeader(302, location.substring(location.lastIndexOf(".") + 1), location, url);
        }
    }
}
