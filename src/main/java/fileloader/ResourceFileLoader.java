package fileloader;

import fileloader.exception.LoadFileFailedException;
import fileloader.exception.NoSuchFileException;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.AccessDeniedException;

public class ResourceFileLoader implements FileLoader {
    private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";

    public ResourceFileLoader() {
    }

    @Override
    public byte[] loadFile(String filePath) throws NoSuchFileException {
        try {
            return FileIoUtils.loadFileFromClasspath(String.format("%s%s", STATIC_RESOURCE_PATH_PREFIX, filePath));
        } catch (NullPointerException | AccessDeniedException e) {
            throw new NoSuchFileException(filePath);
        } catch (IOException | URISyntaxException e) {
            throw new LoadFileFailedException(e);
        }
    }
}
