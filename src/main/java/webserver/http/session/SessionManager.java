package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import webserver.exception.NotFoundSessionException;

public class SessionManager {
	private Map<String, Session> sessions;

	public SessionManager() {
		this.sessions = new HashMap<>();
	}

	public String generateInitialSession() {
		String uuid = UUID.randomUUID().toString();
		sessions.put(uuid, new Session(uuid));
		return uuid;
	}

	public void addSessionAttribute(String sessionId, String attributeName, String attributeValue) {
		Session session = sessions.get(sessionId);

		if (Objects.nonNull(session)) {
			session.setAttribute(attributeName, attributeValue);
			return;
		}
		throw new NotFoundSessionException();
	}
}
