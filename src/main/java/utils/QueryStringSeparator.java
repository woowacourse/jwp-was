package utils;

import java.util.HashMap;
import java.util.Map;

public class QueryStringSeparator {
	public static final String QUERY_PARAMS_SEPARATOR = "&";

	public static Map<String, String> separate(String queryString) {
		Map<String, String> params = new HashMap<>();
		String[] queryParams = queryString.split(QUERY_PARAMS_SEPARATOR);

		for (String queryParam : queryParams) {
			String[] param = queryParam.split("=");
			params.put(param[0], param[1]);
		}
		return params;
	}
}
