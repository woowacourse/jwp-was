package model;

import java.util.Map;

public class QueryString {
	private final Map<String, String> queryString;

	public QueryString(final Map<String, String> queryString) {
		this.queryString = queryString;
	}

	public String getQueryString(String key) {
		return queryString.get(key);
	}
}
