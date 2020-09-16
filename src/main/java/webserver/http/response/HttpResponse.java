package webserver.http.response;

import db.DataBase;
import model.User;
import model.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequests;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public abstract class HttpResponse {
    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);
    protected static final String URL_PARAMETER_REGEX = "&";
    protected static final String PARAMETER_REGEX = "=";
    protected static final String ENCODING_TYPE = "UTF-8";

    protected HttpRequests httpRequests;

    void createUser(Map<String, String> parameters) {
        User user = UserFactory.create(parameters);
        DataBase.addUser(user);
    }

    public void initHttpRequests(HttpRequests httpRequests) {
        this.httpRequests = httpRequests;
    }

    public abstract void handleResponse(DataOutputStream dos) throws IOException, URISyntaxException;
}
