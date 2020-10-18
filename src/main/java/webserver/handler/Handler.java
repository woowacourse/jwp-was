package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.HttpRequest;
import webserver.RequestHandler;

public abstract class Handler {
	protected static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	public abstract void handleRequest(HttpRequest httpRequest, DataOutputStream dos) throws
		IOException, URISyntaxException;

	public abstract boolean canHandle(String path);
}
