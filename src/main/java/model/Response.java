package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import exception.NotFoundElementException;

public class Response {
	private final Map<String, String> header;

	public Response(final Map<String, String> header) {
		this.header = header;
	}

	public String getRequestElement(String key) {
		String value = header.get(key);
		if (Objects.isNull(value)) {
			throw new NotFoundElementException();
		}
		return header.get(key);
	}

	public List<String> getAllHeaders() {
		List<String> headers = new LinkedList<>();
		for(String key : header.keySet()) {
			headers.add(key + ": " + header.get(key));
		}
		return headers;
	}
}
