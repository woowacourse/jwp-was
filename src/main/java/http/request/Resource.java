package http.request;

import http.MediaType;

import java.util.Objects;

public class Resource {
	private String path;
	private String target;
	private MediaType mediaType;

	public Resource(String path, String target, MediaType mediaType) {
		this.path = path;
		this.target = target;
		this.mediaType = mediaType;
	}

	public String getUri() {
		return path + target;
	}

	@Override
	public String toString() {
		return "Resource{" +
				"path='" + path + '\'' +
				", fileName='" + target + '\'' +
				", mediaType=" + mediaType +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Resource resource = (Resource) o;
		return path.equals(resource.path) &&
				target.equals(resource.target) &&
				mediaType == resource.mediaType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, target, mediaType);
	}
}
