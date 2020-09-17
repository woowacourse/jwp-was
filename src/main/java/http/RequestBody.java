package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final Map<String, String> params;

    public RequestBody(BufferedReader br, int contentLength) throws IOException {
        params = new HashMap<>();
        String line = IOUtils.readData(br, contentLength);
        if (line.isEmpty()) {
            throw new IllegalArgumentException("Line is empty."); //TODO 예외 발생-> 적절한 응답코드 나타내자-j
        }

        String[] tokens = line.split("&");
        for (String token : tokens) {
            String[] keyValue = token.split("="); //TODO 중복되는 키가 들어오거나 value가 없는 경우에는 어떤 정책을 가져갈지.. -h
            params.put(keyValue[0], keyValue[1]);
        }
    }

    public String getValue(String key) {
        return this.params.get(key);
    }
}
