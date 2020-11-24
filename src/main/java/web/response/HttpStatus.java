package web.response;

public enum HttpStatus {
	OK("200", "OK"), FOUND("302", "FOUND");

	private String statusCode;
	private String status;

	HttpStatus(String statusCode, String status) {
		this.statusCode = statusCode;
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatus() {
		return status;
	}
}
