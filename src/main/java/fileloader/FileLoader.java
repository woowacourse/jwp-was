package fileloader;

public interface FileLoader {
    byte[] loadFile(String filePath) throws NonExistentFileException;
}
