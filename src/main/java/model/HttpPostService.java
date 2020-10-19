package model;

import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;

public class HttpPostService extends AbstractHttpService {
    private static final Logger logger = LoggerFactory.getLogger(HttpPostService.class);

    public HttpPostService(HttpHeader header, HttpBody body) {
        super(header, body);
    }

    @Override
    public void doService(OutputStream out) {
        HttpParams bodyParams = HttpParams.of(body.getBody());
        DataBase.addUser(bodyParams.toModel(User.class));
        DataBase.findAll().forEach(user -> logger.debug(user.toString()));
    }
}
