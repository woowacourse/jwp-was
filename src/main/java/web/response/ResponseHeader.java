package web.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseHeader {
	private static final String HEADER_DELIMITER = ": ";
	private static final String LINE_SEPARATOR = System.lineSeparator();

	private Map<String, String> responseHeader;

	public ResponseHeader(Map<String, String> responseHeader) {
		this.responseHeader = responseHeader;
	}

	public void put(String key, String value) {
		responseHeader.put(key, value);
	}

	public void writeAll(DataOutputStream dataOutputStream) throws IOException {
		for(String header : responseHeader.keySet()){
			dataOutputStream.writeBytes(header + HEADER_DELIMITER + responseHeader.get(header) + LINE_SEPARATOR);
		}
	}
}
