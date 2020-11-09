package webserver;

import java.util.Arrays;

public enum LoginStatus {
	SUCCESS("/index.html", true),
	FAIL("/user/login_failed.html", false);

	private final String redirectUrl;
	private final boolean validated;

	LoginStatus(String redirectUrl, boolean validated) {
		this.redirectUrl = redirectUrl;
		this.validated = validated;
	}

	public static LoginStatus of(boolean validated) {
		return Arrays.stream(LoginStatus.values())
			.filter(loginStatus -> loginStatus.validated == validated)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로그인 상태입니다."));
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public boolean isValidated() {
		return validated;
	}
}
