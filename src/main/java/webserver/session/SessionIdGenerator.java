package webserver.session;

import java.util.UUID;

public class SessionIdGenerator {
	protected static String generate() {
		return UUID.randomUUID().toString();
	}
}
