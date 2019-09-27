package webserver;

import webserver.http.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WebTestForm {
    public String getHttpGetStartLine(String path) {
        return "GET " + path + " HTTP1.1";
    }

    public String getHttpPostStartLine(String path) {
        return "POST " + path + " HTTP1.1";
    }

    public String getHttpDefaultHeader() {
        return "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Pragma: no-cache\n" +
                "Cache-Control: no-cache\n" +
                "Content-Length: 26\n" +
                "Cookie: userId=alswns;name=alswns;password=pwd;email=a@a;JSESSIONID=550e8400-e29b-41d4-a716-446655440000\n" +
                "Sec-Fetch-Mode: no-cors\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36\n" +
                "Accept: image/webp,image/apng,image/*,*/*;q=0.8\n" +
                "Sec-Fetch-Site: same-origin\n" +
                "Referer: http://localhost:8080/\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7\n\n";
    }

    public String getHttpDefaultHeaderWithBody() {
        return "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Pragma: no-cache\n" +
                "Cache-Control: no-cache\n" +
                "Content-Length: 26\n" +
                "Cookie: userId=alswns;name=alswns;password=pwd;email=a@a;JSESSIONID=550e8400-e29b-41d4-a716-446655440000\n" +
                "Sec-Fetch-Mode: no-cors\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36\n" +
                "Accept: image/webp,image/apng,image/*,*/*;q=0.8\n" +
                "Sec-Fetch-Site: same-origin\n" +
                "Referer: http://localhost:8080/\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7\n" +
                "\r\n" +
                "userId=alswns&password=pwd";
    }

    public HttpRequest getHttpGetRequest(String path) throws IOException {
        String httpGetStartLine = getHttpGetStartLine(path);
        String httpDefaultHeader = getHttpDefaultHeader();
        String httpRequest = httpGetStartLine + "\n" + httpDefaultHeader;
        InputStream in = new ByteArrayInputStream(httpRequest.getBytes());
        return HttpRequest.create(in);
    }

    public HttpRequest getHttpPostRequestWithBody(String path) throws IOException {
        String httpGetStartLine = getHttpPostStartLine(path);
        String httpDefaultHeader = getHttpDefaultHeaderWithBody();
        String httpRequest = httpGetStartLine + "\n" + httpDefaultHeader;
        InputStream in = new ByteArrayInputStream(httpRequest.getBytes());
        return HttpRequest.create(in);
    }
}
