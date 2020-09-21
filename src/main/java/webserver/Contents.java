package webserver;

import java.io.BufferedReader;
import java.io.IOException;

import utils.IOUtils;

public class Contents {
	private final String body;

	public Contents(BufferedReader br, int contentLength) throws IOException {
		this.body = IOUtils.readData(br, contentLength);
	}

	public String getBody() {
		return body;
	}
}
