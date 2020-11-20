package implementedbehavior;

import db.DataBase;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import webserver.requestmapping.DynamicHtmlGenerator;
import webserver.requestmapping.behavior.RequestBehavior;

public class UserListBehavior implements RequestBehavior {
    @Override
    public void behave(RequestEntity requestEntity, ResponseEntity responseEntity) {
        responseEntity.status(HttpStatus.OK)
            .body(DynamicHtmlGenerator.applyHandlebar("user/list", DataBase.findAll()));
    }
}
