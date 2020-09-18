package http.path;

import http.RequestBody;
import http.RequestHeader;

import java.io.DataOutputStream;

public interface Path {
    void get(DataOutputStream dos, RequestHeader requestHeader);
    void post(DataOutputStream dos, RequestHeader requestHeader, RequestBody requestBody);
}
