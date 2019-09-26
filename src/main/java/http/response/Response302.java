//package http.response;
//
//import http.common.Cookies;
//import http.common.HttpHeader;
//
//import java.io.DataOutputStream;
//import java.io.IOException;
//
//public class Response302 extends HttpResponse {
//
//    public static final String BLANK = " ";
//
//    public Response302(final StatusLine statusLine, final HttpHeader responseHeader, final Cookies cookies, final ResponseBody responseBody) {
//        super(statusLine, responseHeader, responseBody);
//    }
//
//    @Override
//    public void writeResponse(final DataOutputStream dos) {
//        try {
//            dos.writeBytes(statusLine.getHttpVersion() + BLANK + statusLine.getCodeAndStatus() + "\r\n");
//            dos.writeBytes("Location: " + responseHeader.findHeader("Location") + "\r\n");
//            dos.writeBytes(cookies.toString());
//            responseBody(dos, responseBody.getBody());
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
//}
