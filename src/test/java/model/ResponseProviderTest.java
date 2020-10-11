package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ResponseProviderTest {

    @ParameterizedTest
    @DisplayName("ResponseProvider 생성 테스트")
    @ValueSource(strings = {
        "src/test/resources/input/get_template_file_request.txt",
        "src/test/resources/input/get_static_file_request.txt",
        "src/test/resources/input/get_api_request.txt",
        "src/test/resources/input/post_api_request.txt"
    })
    void create(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        ResponseProvider responseProvider = ResponseProvider.of(request);

        assertThat(responseProvider).isInstanceOf(ResponseProvider.class);
    }

    @ParameterizedTest
    @DisplayName("executeOperation 테스트")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt"
            + ":src/test/resources/output/get_template_file_request_output.txt"
            + ":200 OK",
        "src/test/resources/input/get_static_file_request.txt"
            + ":src/test/resources/output/get_static_file_request_output.txt"
            + ":200 OK",
        "src/test/resources/input/get_api_request.txt"
            + ":src/test/resources/output/get_api_request_output.txt"
            + ":200 OK",
        "src/test/resources/input/post_api_request.txt"
            + ":src/test/resources/output/post_api_request_output.txt"
            + ":302 FOUND"
    }, delimiter = ':')
    void executeOperation(String inputFilePath, String outputFilePath, String responseFirstLine)
        throws IOException {
        InputStream inputStream = new FileInputStream(inputFilePath);
        Request request = Request.of(inputStream);
        ResponseProvider responseProvider = ResponseProvider.of(request);

        FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
        DataOutputStream outputStream = new DataOutputStream(fileOutputStream);

        responseProvider.executeOperation(outputStream, request);
        outputStream.close();
        fileOutputStream.close();

        InputStream resultInputStream = new FileInputStream(outputFilePath);
        InputStreamReader inputStreamReader = new InputStreamReader(resultInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String outputFirstLine = bufferedReader.readLine();
        assertThat(outputFirstLine).contains(responseFirstLine);
    }
}
