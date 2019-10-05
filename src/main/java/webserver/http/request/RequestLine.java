package webserver.http.request;

import java.util.Map;
import java.util.Objects;

import webserver.exception.NotFoundElementException;

public class RequestLine {
	private Map<String, String> requestLine;

	public RequestLine(final Map<String, String> requestLine) {
		this.requestLine = requestLine;
	}

	public String getElementValue(String elementKey) {
		String elementValue = requestLine.get(elementKey);

		if(Objects.nonNull(elementValue)) {
			return elementValue;
		}

		throw new NotFoundElementException();
	}
}
