package utils;

import org.slf4j.Logger;

public class LoggingUtils {
    public static void logStackTrace(Logger logger, Exception e) {
        StringBuilder logBuilder = new StringBuilder();
        StackTraceElement[] traces = e.getStackTrace();

        for (StackTraceElement traceElement : traces) {
            logBuilder.append("\tat ").append(traceElement).append("\n");
        }
        logBuilder.insert(0, "Exception : " + e.getMessage() + "\n");
        logger.error(logBuilder.toString());
    }
}
