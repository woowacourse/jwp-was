package jwp.was.webapplicationserver.configure.annotation;

import java.lang.annotation.Annotation;

public class AnnotationChecker {

    private static final int HAS_NOT_SIZE = 0;

    private AnnotationChecker() {
    }

    public static <T extends Annotation> boolean includeAnnotation(Object configureInstance,
        Class<T> annotation) {

        boolean hasAnnotation = configureInstance.getClass()
            .getDeclaredAnnotationsByType(annotation).length != HAS_NOT_SIZE;

        Annotation[] declaredAnnotations = configureInstance.getClass().getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (hasAnnotation) {
                return true;
            }
            hasAnnotation = declaredAnnotation.annotationType()
                .getDeclaredAnnotationsByType(annotation).length != HAS_NOT_SIZE;
        }
        return hasAnnotation;
    }
}
