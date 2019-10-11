package helper;

import webserver.session.IdGenerationStrategy;

public class StorageHelper {
    public static IdGenerationStrategy idGenerator(String id) {
        return () -> id;
    }
}
