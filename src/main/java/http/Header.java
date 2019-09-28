package http;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import exception.NotFoundElementException;

public class Header {
	private Map<HeaderElement, String> header;

	public Header(Map<HeaderElement, String> header) {
		this.header = header;
	}

	public String getElementValue(HeaderElement element) {
		String elementValue = header.get(element);

		if(!Objects.isNull(element)) {
			return elementValue;
		}
		throw new NotFoundElementException(); //TODO : 상황에 맞는 에러 메시지를 던질 것.
	}

	public List<String> printHeader() {
		List<String> headers = new LinkedList<>();
		for (HeaderElement attribute : header.keySet()) {
			headers.add(String.format("%s : %s", attribute.getElement(),  header.get(attribute)));
		}
		return headers;
	}
}
