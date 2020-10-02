package http.response;

public class ResponseEntity {
    // private static final Logger logger = LoggerFactory.getLogger(ResponseEntity.class);
    //
    // public static void ok(DataOutputStream dos, int lengthOfBodyContent, String contentType,
    //         byte[] body) {
    //     StatusLine statusLine = new StatusLine("HTTP/1.1", Status.OK);
    //     HttpHeader header = new HttpHeader();
    //     header.setHeader(Header.CONTENT_TYPE.getName(), contentType + ";charset=utf-8");
    //     header.setHeader(Header.CONTENT_LENGTH.getName(), lengthOfBodyContent + "");
    //     ResponseBody responseBody = new ResponseBody(body);
    //     Response response = new Response(statusLine, header, responseBody);
    //
    //     try {
    //         dos.writeBytes(response.getStatusLine().toString());
    //         for (String key : header.getRequestHeaders().keySet()) {
    //             dos.writeBytes(key + ": " + header.getHeader(key) + "\r\n");
    //         }
    //         dos.writeBytes("\r\n");
    //         dos.write(response.getBody().getBody(), 0, header.getContentLength());
    //         dos.flush();
    //     } catch (IOException e) {
    //         logger.error(e.getMessage());
    //     }
    // }
    //
    // public static void response302(DataOutputStream dos, String locationUri) {
    //     try {
    //         dos.writeBytes("HTTP/1.1 302 Found \r\n");
    //         dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
    //         dos.writeBytes("Location: " + locationUri);
    //         dos.writeBytes("\r\n");
    //     } catch (IOException e) {
    //         logger.error(e.getMessage());
    //     }
    // }
}
