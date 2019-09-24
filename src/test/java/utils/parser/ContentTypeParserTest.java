package utils.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.exception.NotFoundContentTypeSeparatorException;
import webserver.http.headerfields.Chemical;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentTypeParserTest {
    @Test
    @DisplayName("헤더의 Content-Type의 매개변수를 key value로 설정한다.")
    void contentTypeParser() {
        ContentTypeParser contentTypeParser = new ContentTypeParser();
        String inputData = "Content-Type: application/x-www-form-urlencoded; charset=utf-8";
        Map<String, String> result = contentTypeParser.toMap(inputData);

        assertThat(result.get("charset")).isEqualTo("utf-8");
    }

    @Test
    @DisplayName("String 타입의 Content-Type을 ';'로 분리하여 List 형태로 변환한다.")
    void convertContentType() {
        String inputData = "application/x-www-form-urlencoded; charset=utf-8";

        List<String> result = ContentTypeParser.convertContentType(inputData);

        assertThat(result.get(0)).isEqualTo("application/x-www-form-urlencoded");
        assertThat(result.get(1)).isEqualTo("charset=utf-8");
    }

    @Test
    @DisplayName("String 형태의 Content-Type 한 줄을 입력받아 Chemical 타입으로 변환한다.")
    void chemicalParse() {
        String inputData1 = "application/x-www-form-urlencoded";
        String inputData2 = "text/html";
        String inputData3 = "image/gif";
        String inputData4 = "audio/file";
        String inputData5 = "font/fontFile";

        assertThat(ContentTypeParser.chemicalParse(inputData1)).isEqualTo(Chemical.APPLICATION);
        assertThat(ContentTypeParser.chemicalParse(inputData2)).isEqualTo(Chemical.TEXT);
        assertThat(ContentTypeParser.chemicalParse(inputData3)).isEqualTo(Chemical.IMAGE);
        assertThat(ContentTypeParser.chemicalParse(inputData4)).isEqualTo(Chemical.AUDIO);
        assertThat(ContentTypeParser.chemicalParse(inputData5)).isEqualTo(Chemical.FONT);
    }

    @Test
    @DisplayName("존재하지 않는 Chemical 타입을 변환하는 경우 실패한다.")
    void chemicalParseFail() {
        String inputData = "test/x-www-form-urlencoded";

        assertThrows(IllegalArgumentException.class, () -> ContentTypeParser.chemicalParse(inputData));
    }

    @Test
    @DisplayName("Chemical 타입을 파싱하려고 할 때, Content-Type '/'가 존재하지 않는 경우 예외가 발생한다.")
    void chemicalParseFail2() {
        String inputData1 = "application";
        String inputData2 = "application=x-www-form-urlencoded";
        String inputData3 = "application:x-www-form-urlencoded";
        String inputData4 = "application&x-www-form-urlencoded";

        assertThrows(NotFoundContentTypeSeparatorException.class, () -> ContentTypeParser.chemicalParse(inputData1));
        assertThrows(NotFoundContentTypeSeparatorException.class, () -> ContentTypeParser.chemicalParse(inputData2));
        assertThrows(NotFoundContentTypeSeparatorException.class, () -> ContentTypeParser.chemicalParse(inputData3));
        assertThrows(NotFoundContentTypeSeparatorException.class, () -> ContentTypeParser.chemicalParse(inputData4));
    }

    @Test
    @DisplayName("String 형태의 Content-Type 한 줄을 입력받아 subtype을 파싱한다.")
    void subtypeParse() {
        String inputData1 = "application/x-www-form-urlencoded";
        String inputData2 = "text/html";
        String inputData3 = "image/gif";
        String inputData4 = "audio/audioFile";
        String inputData5 = "font/fontFile";

        assertThat(ContentTypeParser.subtypeParse(inputData1)).isEqualTo("x-www-form-urlencoded");
        assertThat(ContentTypeParser.subtypeParse(inputData2)).isEqualTo("html");
        assertThat(ContentTypeParser.subtypeParse(inputData3)).isEqualTo("gif");
        assertThat(ContentTypeParser.subtypeParse(inputData4)).isEqualTo("audioFile");
        assertThat(ContentTypeParser.subtypeParse(inputData5)).isEqualTo("fontFile");
    }

    @Test
    @DisplayName("subtype을 파싱하려고 할 때, Content-Type '/'가 존재하지 않는 경우 예외가 발생한다.")
    void subtypeParseFail() {
        String inputData1 = "application";
        String inputData2 = "application=x-www-form-urlencoded";
        String inputData3 = "application:x-www-form-urlencoded";
        String inputData4 = "application&x-www-form-urlencoded";

        assertThrows(NotFoundContentTypeSeparatorException.class, () -> ContentTypeParser.subtypeParse(inputData1));
        assertThrows(NotFoundContentTypeSeparatorException.class, () -> ContentTypeParser.subtypeParse(inputData2));
        assertThrows(NotFoundContentTypeSeparatorException.class, () -> ContentTypeParser.subtypeParse(inputData3));
        assertThrows(NotFoundContentTypeSeparatorException.class, () -> ContentTypeParser.subtypeParse(inputData4));
    }
}