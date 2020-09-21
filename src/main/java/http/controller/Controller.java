package http.controller;

import http.RequestBody;
import http.RequestHeader;

import java.io.DataOutputStream;

public interface Controller {
    void get(DataOutputStream dos, RequestHeader requestHeader);
    void post(DataOutputStream dos, RequestHeader requestHeader, RequestBody requestBody);
}
