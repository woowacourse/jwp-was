package utils.parser;

import org.junit.jupiter.api.Test;
import utils.parser.jsonelements.JsonArray;
import utils.parser.jsonelements.JsonObject;

import static org.assertj.core.api.Assertions.assertThat;

class JsonParserTest {
    private final JsonParser jsonParser = new JsonParser();

    @Test
    void emptyTest() {
        final String EMPTY = " {   }";
        final JsonObject result = jsonParser.interpret(EMPTY);
        System.out.println(result);
        assertThat(result.toString()).isEqualTo("{}");
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void singleAttributeObjectTest() {
        final String SINGLE_ATTRIBUTE = "{      \"Woowa\" :   \"Why not Wooa?\" ,}   ";
        final JsonObject result = jsonParser.interpret(SINGLE_ATTRIBUTE);
        System.out.println(result);
        assertThat(result.toString()).isEqualTo("{\"Woowa\": \"Why not Wooa?\"}");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get("Woowa").toString()).isEqualTo("\"Why not Wooa?\"");
    }

    @Test
    void doubleAttributesTest() {
        final String DOUBLE_ATTRIBUTES = "{   \"Name\" :  \"Woowa\", \"Level\":  1.1e-1}   ";
        final JsonObject result = jsonParser.interpret(DOUBLE_ATTRIBUTES);
        System.out.println(result);
        assertThat(result.get("Name").val()).isEqualTo("Woowa");
        assertThat(result.get("Level").val()).isEqualTo(0.11);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void multipleAttributesTest() {
        final String MULTIPLE_ATTRIBUTES = "{ \"Name\" :\"{\" ,\"Level\":-6 , \"cat\" : false, \"Wheel\": 3.5e-5, } ";
        final JsonObject result = jsonParser.interpret(MULTIPLE_ATTRIBUTES);
        System.out.println(result);
        assertThat(result.get("Name").val()).isEqualTo("{");
        assertThat(result.get("Level").val()).isEqualTo(-6);
        assertThat(result.get("cat").val()).isEqualTo(false);
        assertThat(result.get("Wheel").val()).isEqualTo(0.000035);
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    void nestedAttributesTest() {
        final String NESTED_ATTRIBUTES = "{\"Name\" :{ \"Level}}\":-998 , \"cat\" : false} } ";
        final JsonObject result = jsonParser.interpret(NESTED_ATTRIBUTES);
        final JsonObject innerKey = (JsonObject) result.get("Name");
        System.out.println(result);
        assertThat(innerKey.get("Level}}").val()).isEqualTo(-998);
        assertThat(innerKey.get("cat").val()).isEqualTo(false);
        assertThat(result.size()).isEqualTo(1);
        assertThat(innerKey.size()).isEqualTo(2);
    }

    @Test
    void arrayTest() {
        final String ARRAY = "{\"Name\" : [null ,-6 , \"cat\",false, 3.5e-5, { \"\": 6}]} ";
        final JsonObject result = jsonParser.interpret(ARRAY);
        System.out.println(result);
        assertThat(result.size()).isEqualTo(1);
        assertThat(((JsonArray) result.get("Name")).size()).isEqualTo(6);
    }

    @Test
    void routerConfigTest() {
        final String CONFIG =
                "{\n" +
                "  \"GET\": {\n" +
                "    \"/\" : {\n" +
                "      \"controller\": \"IndexController\",\n" +
                "      \"method\": \"index\"\n" +
                "    },\n" +
                "    \"/test/{num}\" : {\n" +
                "      \"controller\": \"TestController\",\n" +
                "      \"method\": \"test\"\n" +
                "    }\n" +
                "  },\n" +
                "\n" +
                "  \"POST\": {\n" +
                "    \"/user/create\": {\n" +
                "      \"controller\": \"UserController\",\n" +
                "      \"method\": \"signUp\"\n" +
                "    }\n" +
                "  },\n" +
                "\n" +
                "  \"PUT\": {},\n" +
                "\n" +
                "  \"DELETE\": {}\n" +
                "}";
        final JsonObject result = jsonParser.interpret(CONFIG);
        System.out.println(result);
        assertThat(result.size()).isEqualTo(4);
        final JsonObject get = (JsonObject) result.get("GET");
        assertThat(get.size()).isEqualTo(2);
        final JsonObject root = (JsonObject) get.get("/");
        assertThat(root.size()).isEqualTo(2);
        assertThat((String) root.get("controller").val()).isEqualTo("IndexController");
        assertThat((String) root.get("method").val()).isEqualTo("index");
        final JsonObject test_num = (JsonObject) get.get("/test/{num}");
        assertThat(test_num.size()).isEqualTo(2);
        assertThat((String) test_num.get("controller").val()).isEqualTo("TestController");
        assertThat((String) test_num.get("method").val()).isEqualTo("test");
        final JsonObject post = (JsonObject) result.get("POST");
        assertThat(post.size()).isEqualTo(1);
        final JsonObject user_create = (JsonObject) post.get("/user/create");
        assertThat(user_create.size()).isEqualTo(2);
        assertThat((String) user_create.get("controller").val()).isEqualTo("UserController");
        assertThat((String) user_create.get("method").val()).isEqualTo("signUp");
        assertThat(((JsonObject) result.get("PUT")).size()).isEqualTo(0);
        assertThat(((JsonObject) result.get("DELETE")).size()).isEqualTo(0);
    }
}