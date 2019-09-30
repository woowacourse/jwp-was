package utils.modelandview.model;

import java.util.Map;

public interface Model {
    void putData(String name, Object o);

    Map<String, Object> getData();
}
