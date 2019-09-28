package fileloader;

public class TestFileLoader implements FileLoader {
    public static final String WRONG_PATH = "/WRONG_PATH";

    @Override
    public byte[] loadFile(String filePath) throws NonExistentFileException {
        if (filePath.equals(WRONG_PATH)) {
            throw new NonExistentFileException(WRONG_PATH);
        }
        return new byte[0];
    }
}