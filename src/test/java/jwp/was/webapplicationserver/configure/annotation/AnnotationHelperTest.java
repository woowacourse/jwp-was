package jwp.was.webapplicationserver.configure.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import jwp.was.util.annotation.IncludedAnnotation;
import jwp.was.util.annotation.IncludedClass;
import jwp.was.util.annotation.SoleAnnotation;
import jwp.was.util.annotation.SoleClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnnotationHelperTest {

    private static final IncludedClass INCLUDED_CLASS_INSTANCE = new IncludedClass();
    private static final SoleClass SOLE_CLASS_INSTANCE = new SoleClass();

    @DisplayName("Annotaion가 포함되어 있는지, True - Annotation을 가지고 있는 경우")
    @Test
    void includeAnnotation_HasAnnotation_ReturnTrue() {
        boolean withMainAnnotation
            = AnnotationHelper.includeAnnotation(INCLUDED_CLASS_INSTANCE, IncludedAnnotation.class);

        assertThat(withMainAnnotation).isTrue();
    }

    @DisplayName("Annotaion가 포함되어 있는지, True - Annotation이 포함된 경우")
    @Test
    void includeAnnotation_includeAnnotation_ReturnTrue() {
        boolean withMainAnnotation
            = AnnotationHelper.includeAnnotation(INCLUDED_CLASS_INSTANCE, SoleAnnotation.class);

        assertThat(withMainAnnotation).isTrue();
    }

    @DisplayName("Annotaion가 포함되어 있는지, False - Annotation을 가지고 있지도, 포함되어 있지도 않은 경우")
    @Test
    void includeAnnotation_HasNotAndExcludeAnnotation_ReturnFalse() {
        boolean withMainAnnotation
            = AnnotationHelper.includeAnnotation(SOLE_CLASS_INSTANCE, IncludedAnnotation.class);

        assertThat(withMainAnnotation).isFalse();
    }
}
