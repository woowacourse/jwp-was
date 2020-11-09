package web.response;

public class ResponseLine {
	private HttpStatus status;
	private String httpVersion = "HTTP/1.1";

	public ResponseLine(HttpStatus status) {
		this.status = status;
	}

	public String toString() {
		return httpVersion + " " + status.getStatusCode() + " " + status.getStatus();
	}
}