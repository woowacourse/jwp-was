package dev.luffy.annotation;

import dev.luffy.http.request.HttpRequestMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String path() default "";

    HttpRequestMethod method() default HttpRequestMethod.GET;
}
