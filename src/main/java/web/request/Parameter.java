package web.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;
import utils.RequestParameterUtils;

public class Parameter {
	private Map<String, String> parameterInfo;

	public Parameter(Map<String, String> parameterInfo) {
		this.parameterInfo = parameterInfo;
	}

	public static Parameter of(BufferedReader br, RequestLine requestLine, RequestHeader requestHeader) throws
		IOException {
		Map<String, String> parameters = new HashMap<>();

		parameters.putAll(RequestParameterUtils.extractRequestParameters(requestLine));

		if (requestLine.getMethod().equals(HttpMethod.POST)) {
			String body = IOUtils.readData(br, requestHeader.getContentLength());
			parameters.putAll(RequestParameterUtils.extractBodyParameters(body));
		}

		return new Parameter(parameters);
	}

	public String get(String key) {
		return parameterInfo.get(key);
	}
}
