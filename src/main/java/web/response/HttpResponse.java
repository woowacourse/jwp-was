package web.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import utils.RequestPathUtil;
import web.ContentType;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
	private static final String CHARSET_UTF_8 = ";charset=utf-8";
	private static final String LINE_SEPARATOR = System.lineSeparator();

	private DataOutputStream dataOutputStream;
	private ResponseHeader responseHeader;

	public HttpResponse(DataOutputStream dataOutputStream) {
		this.dataOutputStream = dataOutputStream;
		this.responseHeader = new ResponseHeader(new HashMap<>());
	}

	public static HttpResponse of(OutputStream outputStream) {
		return new HttpResponse(new DataOutputStream(outputStream));
	}

	public void addHeader(String key, String value) {
		responseHeader.put(key, value);
	}

	public void forward(String uri) {
		try {
			String requestPath = RequestPathUtil.extractFilePath(uri);
			byte[] body = FileIoUtils.loadFileFromClasspath(requestPath);
			responseHeader.put("Content-Type", ContentType.of(uri).getContentType() + CHARSET_UTF_8);
			response200Header(body.length);
			responseBody(body);
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}

	public void response200Header(int lengthOfBodyContent) {
		ResponseLine responseLine = new ResponseLine(HttpStatus.OK);
		responseHeader.put("Content-length", String.valueOf(lengthOfBodyContent));
		try {
			dataOutputStream.writeBytes(responseLine.toString() + LINE_SEPARATOR);
			responseHeader.writeAll(dataOutputStream);
			dataOutputStream.writeBytes(LINE_SEPARATOR);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void responseBody(byte[] body) {
		try {
			dataOutputStream.write(body, 0, body.length);
			dataOutputStream.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendRedirect(String uri) {
		ResponseLine responseLine = new ResponseLine(HttpStatus.FOUND);
		responseHeader.put("Location", uri);
		responseHeader.put("Content-Type", "text/html" + CHARSET_UTF_8);
		try {
			dataOutputStream.writeBytes(responseLine.toString() + LINE_SEPARATOR);
			responseHeader.writeAll(dataOutputStream);
			dataOutputStream.writeBytes(LINE_SEPARATOR);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
