package fileloader;

import fileloader.exception.NoSuchFileException;

public class TestFileLoader implements FileLoader {
    public static final String WRONG_PATH = "/WRONG_PATH";

    @Override
    public byte[] loadFile(String filePath) throws NoSuchFileException {
        if (filePath.equals(WRONG_PATH)) {
            throw new NoSuchFileException(WRONG_PATH);
        }
        return new byte[0];
    }
}