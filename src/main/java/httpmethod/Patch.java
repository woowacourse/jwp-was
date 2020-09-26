package httpmethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.Model;

public class Patch implements Method {
    @Override
    public Map<String, String> extractParameter(BufferedReader br, Map<String, String> header) throws IOException {
        return new HashMap<>();
    }

    @Override
    public Model extractModel(String path, Map<String, String> parameter) {
        return null;
    }

    @Override
    public String getName() {
        return "PATCH";
    }
}
