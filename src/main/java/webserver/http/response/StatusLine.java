package webserver.http.response;

import java.util.Map;
import java.util.Objects;

import webserver.exception.NotFoundElementException;

public class StatusLine {
	private Map<String, String> statusLine;

	public StatusLine(final Map<String, String> statusLine) {
		this.statusLine = statusLine;
	}

	public String getElementValue(String elementName) {
		String elementValue = statusLine.get(elementName);

		if(Objects.nonNull(elementName)) {
			return elementValue;
		}
		throw new NotFoundElementException();
	}
}
