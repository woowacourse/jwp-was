package utils.parser;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonParserTest {
    private final JsonParser jsonParser = new JsonParser();

    @Test
    void empty() {
        final String EMPTY = " {   }";
        final JsonObject result = jsonParser.interpret(EMPTY);
        System.out.println(result);
        assertThat(result.toString()).isEqualTo("{}");
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void singleAttributeObject() {
        final String SINGLE_ATTRIBUTE = "{      \"Woowa\" :   \"Why not Wooa?\" }   ";
        final JsonObject result = jsonParser.interpret(SINGLE_ATTRIBUTE);
        System.out.println(result);
        assertThat(result.toString()).isEqualTo("{\"Woowa\": \"Why not Wooa?\"}");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get("Woowa").get().toString()).isEqualTo("\"Why not Wooa?\"");
    }

    @Test
    void doubleAttributes() {
        final String DOUBLE_ATTRIBUTES = "{   \"Name\" :  \"Woowa\", \"Level\":  3   }   ";
        final JsonObject result = jsonParser.interpret(DOUBLE_ATTRIBUTES);
        System.out.println(result);
        assertThat(result.get("Name").get().val()).isEqualTo("Woowa");
        assertThat(result.get("Level").get().val()).isEqualTo(3);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void multipleAttributes() {
        final String MULTIPLE_ATTRIBUTES = "{  \"Name\" :null ,\"Level\":-6 , \"cat\" : false, \"Wheel\": 3.5e-5 } ";
        final JsonObject result = jsonParser.interpret(MULTIPLE_ATTRIBUTES);
        System.out.println(result);
        assertThat(result.get("Name").get().val()).isEqualTo(null);
        assertThat(result.get("Level").get().val()).isEqualTo(-6);
        assertThat(result.get("cat").get().val()).isEqualTo(false);
        assertThat(result.get("Wheel").get().val()).isEqualTo(0.000035);
        assertThat(result.size()).isEqualTo(4);
    }

    /*
    아직 배열은 작동 안 함
     */
    @Test
    void array() {
        final String ARRAY = "{  \"Name\" : [null ,-6 , \"cat\",false, 3.5e-5] } ";
        final JsonObject result = jsonParser.interpret(ARRAY);
        System.out.println(result);
        assertThat(result.size()).isEqualTo(1);
    }

    /*
    중첩도..
     */
    @Test
    void nestedAttributes() {
        final String NESTED_ATTRIBUTES = "{  \"Name\" :{ \"Level\":-6 , \"cat\" : false} } ";
        final JsonObject result = jsonParser.interpret(NESTED_ATTRIBUTES);
        System.out.println(result);
        assertThat(result.size()).isEqualTo(4);
    }
}