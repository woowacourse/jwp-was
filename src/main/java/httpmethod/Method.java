package httpmethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import model.Model;

public interface Method {
    Map<String, String> extractParameter(BufferedReader br, Map<String, String> header) throws IOException;

    Model extractModel(String path, Map<String, String> parameter);

    String getName();
}
