package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
	private HttpResponseStatusLine httpResponseStatusLine;
	private HttpResponseHeader httpResponseHeader;
	private HttpResponseBody httpResponseBody;

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
}
