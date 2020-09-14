package http.request;

import stringprocessor.Params;
import utils.IOUtils;

public class HttpUrl {

    private static final String PATH_PARAM_DELIMITER = "\\?";
    public static final int PARAM_EXIST_SIZE = 2;
    public static final int PATH_INDEX = 0;
    public static final int PARAM_INDEX = 1;
    public static final int SPLIT_SIZE = 2;

    private String path;
    private Params params;

    private HttpUrl(String path) {
        this.path = path;
        this.params = Params.empty();
    }

    private HttpUrl(String path, Params params) {
        this.path = path;
        this.params = params;
    }

    public static HttpUrl from(String url) {
        String decodedUrl = IOUtils.decode(url);
        String[] splittedUrl = decodedUrl.split(PATH_PARAM_DELIMITER, SPLIT_SIZE);

        if (splittedUrl.length == PARAM_EXIST_SIZE) {
            String path = splittedUrl[PATH_INDEX];
            String paramBundle = splittedUrl[PARAM_INDEX];
            return new HttpUrl(path, Params.from(paramBundle));
        }
        return new HttpUrl(splittedUrl[PATH_INDEX]);
    }

    public String getPath() {
        return path;
    }

    public Params getParams() {
        return params;
    }
}
