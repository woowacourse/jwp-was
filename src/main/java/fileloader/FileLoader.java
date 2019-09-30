package fileloader;

import fileloader.exception.NoSuchFileException;

public interface FileLoader {
    byte[] loadFile(String filePath) throws NoSuchFileException;
}
