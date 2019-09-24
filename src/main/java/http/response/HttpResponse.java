package http.response;

import http.MediaType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
	private HttpResponseStatusLine httpResponseStatusLine;
	private HttpResponseHeader httpResponseHeader;
	private HttpResponseBody httpResponseBody;

	public HttpResponse() {
	}

	public HttpResponseStatusLine getHttpResponseStatusLine() {
		return httpResponseStatusLine;
	}

	public HttpResponseHeader getHttpResponseHeader() {
		return httpResponseHeader;
	}

	public HttpResponseBody getHttpResponseBody() {
		return httpResponseBody;
	}

	public void setHttpResponseStatusLine(HttpResponseStatusLine httpResponseStatusLine) {
		this.httpResponseStatusLine = httpResponseStatusLine;
	}

	public void setHttpResponseHeader(HttpResponseHeader httpResponseHeader) {
		this.httpResponseHeader = httpResponseHeader;
	}

	public void setHttpResponseBody(HttpResponseBody httpResponseBody) {
		this.httpResponseBody = httpResponseBody;
	}

	public void sendResponse(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeBytes(httpResponseStatusLine.toString());
		dataOutputStream.writeBytes(httpResponseHeader.toString());
		dataOutputStream.write(httpResponseBody.getBody(), 0, httpResponseBody.getBody().length);
		dataOutputStream.flush();
	}

	public void sendRedirect(String uri) {
		this.httpResponseStatusLine = new HttpResponseStatusLine("HTTP/1.1 302 FOUND \r\n");
		this.httpResponseHeader = new HttpResponseHeader("Location: /" + uri + "\r\n");
	}

	public void send200Ok(String uri) throws IOException, URISyntaxException {
		String contentType = MediaType.getContentType(uri);

		this.httpResponseStatusLine = new HttpResponseStatusLine("HTTP/1.1 200 OK \r\n");
		this.httpResponseBody = new HttpResponseBody(uri);
		this.httpResponseHeader = new HttpResponseHeader(
				"Content-Type: " + contentType + ";charset=utf-8\r\n"
						+ "Content-Length: " + httpResponseBody.getBodyLength());
	}
}
