package clinet.controller;

import web.ContentType;

public class ResourceController extends AbstractController {
	@Override
	public boolean canHandle(String path) {
		return ContentType.isContainType(path);
	}
}
