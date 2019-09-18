package http.supoort;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class RegexTest {
    @Test
    void name() {
        Pattern pattern = Pattern.compile("/user/create");
        Matcher matcher = pattern.matcher("/user/create?abc");
        assertThat(matcher.find()).isTrue();
    }

    @Test
    void wildcard() {
        Pattern pattern = Pattern.compile("/*");
        Matcher matcher = pattern.matcher("/user/create//");
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    void wildcard2() {
        Pattern pattern = Pattern.compile("/user/*");
        Matcher matcher = pattern.matcher("/index");
        assertThat(matcher.find()).isFalse();
        Matcher matcher1 = pattern.matcher("/user/delete");
        assertThat(matcher1.find()).isTrue();
    }
}
