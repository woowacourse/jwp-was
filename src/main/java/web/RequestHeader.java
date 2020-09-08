package web;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private String requestURI;
    private Map<String,String> headers = new HashMap<>();

    public RequestHeader(BufferedReader bufferedReader){

    }
}
