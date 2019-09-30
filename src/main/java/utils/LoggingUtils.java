package utils;

import org.slf4j.Logger;

public class LoggingUtils {
    public static void logStackTrace(Logger logger, Exception e) {
        StringBuilder loggerBuilder = new StringBuilder();
        StackTraceElement[] traces = e.getStackTrace();

        loggerBuilder.append("Exception : \n");
        for (StackTraceElement traceElement : traces) {
            loggerBuilder.append("\tat ").append(traceElement).append("\n");
        }
        logger.debug(loggerBuilder.toString());
    }
}
