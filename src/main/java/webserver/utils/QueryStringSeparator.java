package webserver.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class QueryStringSeparator {
	public static final String QUERY_PARAMS_SEPARATOR = "&";
	public static final String QUERY_PARAM_SEPARATOR = "=";

	public static Map<String, String> separate(String queryString) {
		Map<String, String> params = new LinkedHashMap<>();
		String[] queryParams = queryString.split(QUERY_PARAMS_SEPARATOR);

		for (String queryParam : queryParams) {
			String[] param = queryParam.split(QUERY_PARAM_SEPARATOR);
			params.put(param[0], param[1]);
		}
		return params;
	}
}
