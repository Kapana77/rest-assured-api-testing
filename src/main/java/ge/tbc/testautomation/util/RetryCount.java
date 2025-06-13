package ge.tbc.testautomation.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface RetryCount {
    int maxRetries() default 2;
}
