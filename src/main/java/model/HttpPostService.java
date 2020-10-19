package model;

import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;

public class HttpPostService implements HttpService {
    private static final Logger logger = LoggerFactory.getLogger(HttpPostService.class);

    @Override
    public void doService(OutputStream out, RequestURI requestURI, HttpBody httpBody) {
        HttpParams bodyParams = HttpParams.of(httpBody.getBody());
        DataBase.addUser(bodyParams.toModel(User.class));
        DataBase.findAll().forEach(user -> logger.debug(user.toString()));
    }
}
