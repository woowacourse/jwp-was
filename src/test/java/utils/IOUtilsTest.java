package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.IOUtils.extractExtension;
import static utils.IOUtils.parseStringToObject;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @Test
    public void parseStringToObjectTest() {
        String param = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> object = parseStringToObject(param);

        assertThat(object.get("userId")).isEqualTo("javajigi");
        assertThat(object.get("email")).isEqualTo("javajigi%40slipp.net");
    }

    @Test
    public void parseFileExtensionTest() {
        String fileName = "vuetify.min";
        String fileExtension = "css";
        String file = fileName + "." + fileExtension;

        assertThat(extractExtension(file)).isEqualTo(fileExtension);
    }
}
