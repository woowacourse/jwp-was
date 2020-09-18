package http.path;

import http.RequestBody;
import http.RequestHeader;
import http.ResponseHeader;

import java.io.DataOutputStream;

public class Index implements Path{
    @Override
    public void get(DataOutputStream dos, RequestHeader requestHeader) {
        ResponseHeader.response302Header(dos, "/index.html");
    }

    @Override
    public void post(DataOutputStream dos, RequestHeader requestHeader, RequestBody requestBody) {
        ResponseHeader.response405Header(dos);
    }
}
