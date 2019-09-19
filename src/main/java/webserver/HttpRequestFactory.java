//package webserver;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//public class HttpRequestFactory {
//
//    public HttpRequest getHttpRequest(InputStream in) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
//
//        HttpStartLine httpStartLine = HttpStartLine.of(br);
//        HttpRequestHeader httpRequestHeader = HttpRequestHeader.of(br);
//
//        if (httpStartLine.isGet()) {
//            return new HttpRequest(httpStartLine, httpRequestHeader);
//        } else if (httpStartLine.isPost()) {
//            return new HttpRequestWithBody(new HttpRequest(httpStartLine, httpRequestHeader), new HttpRequestBody(br, httpRequestHeader.getContentLength()));
//        }
//
//        throw new IllegalArgumentException();
//    }
//}
