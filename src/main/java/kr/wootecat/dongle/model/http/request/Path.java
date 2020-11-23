package kr.wootecat.dongle.model.http.request;

import static java.lang.String.*;

import java.util.regex.Pattern;

import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

class Path {

    private static final Pattern VALID_PATH_PATTERN = Pattern.compile(
            "(/[\\w-]+)+(\\.[\\w]+)*");
    private static final Pattern EXTENSION_PATTERN = Pattern.compile("[^\\s]+\\.[\\w]+");

    private final String path;

    public Path(String path) {
        validatePath(path);
        this.path = path;
    }

    public void validatePath(String path) {
        if (!VALID_PATH_PATTERN.matcher(path).matches()) {
            throw new IllegalRequestDataFormatException(format("유효한 URL 형식이 아닙니다.: %s", path));
        }
    }

    public boolean isStaticPath() {
        return EXTENSION_PATTERN.matcher(path).matches();
    }

    public String getPath() {
        return path;
    }
}
