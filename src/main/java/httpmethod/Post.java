package httpmethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import model.Model;
import utils.IOUtils;
import utils.ModelExtractor;
import utils.RequestUtils;

public class Post implements Method {
    @Override
    public Map<String, String> extractParameter(BufferedReader br, Map<String, String> header) throws IOException {
        String body = IOUtils.readData(br, Integer.parseInt(header.get("Content-Length")));
        return RequestUtils.extractParameter(body);
    }

    @Override
    public Model extractModel(String path, Map<String, String> parameter) {
        String modelType = RequestUtils.extractTitleOfModel(path);
        ModelExtractor modelExtractor = ModelExtractor.valueOf(modelType);
        return modelExtractor.getModel(parameter);
    }

    @Override
    public String getName() {
        return "POST";
    }
}
