package webserver.pageprovider;

import http.request.HttpRequestAccessor;
import http.response.HttpResponseAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.page.FilePage;
import webserver.page.Page;

import java.util.Objects;

public class FilePageProvider implements PageProvider {
    private static final Logger log = LoggerFactory.getLogger(FilePageProvider.class);

    private final String directoryPrefix;

    private FilePageProvider(String directoryPrefix) {
        this.directoryPrefix = directoryPrefix;
    }

    public static FilePageProvider fromDirectory(String directoryPrefix) {
        return new FilePageProvider(directoryPrefix);
    }

    @Override
    public Page provide(HttpRequestAccessor request, HttpResponseAccessor response) {
        String filePath = request.getPath();
        String fullFilePath = String.format("./%s%s", directoryPrefix, filePath);
        log.debug("full filePath: {}", fullFilePath);
        return FilePage.fromPath(fullFilePath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilePageProvider that = (FilePageProvider) o;
        return Objects.equals(directoryPrefix, that.directoryPrefix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directoryPrefix);
    }
}
