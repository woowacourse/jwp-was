package webserver.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import webserver.RequestHeader;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping
public @interface PostMapping {
    RequestHeader.MethodType TYPE = RequestHeader.MethodType.POST;

    String[] value() default "/";
}
