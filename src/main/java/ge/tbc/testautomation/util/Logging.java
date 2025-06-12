package ge.tbc.testautomation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
    public static Logger getLogger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }

}
