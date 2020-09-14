package webserver.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import webserver.RequestHeader;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetMapping {
    RequestHeader.MethodType TYPE = RequestHeader.MethodType.GET;

    String[] value() default "/";
}

