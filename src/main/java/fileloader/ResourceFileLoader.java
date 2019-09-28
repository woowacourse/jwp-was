package fileloader;

import fileloader.exception.LoadFileFailedException;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceFileLoader implements FileLoader {
    private final String prefix;

    public ResourceFileLoader(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public byte[] loadFile(String filePath) throws NonExistentFileException {
        try {
            return FileIoUtils.loadFileFromClasspath(String.format("%s%s", prefix, filePath));
        } catch (NullPointerException e) {
            throw new NonExistentFileException(filePath);
        } catch (IOException | URISyntaxException e) {
            throw new LoadFileFailedException(e);
        }
    }
}
