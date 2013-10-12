package org.minetweak.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This is the Log Formatter for TweakLoggers
 */
public class LogFormatter extends Formatter {
    private SimpleDateFormat dateFormat;
    final TweakLogger logger;

    protected LogFormatter(TweakLogger logger) {
        this.logger = logger;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.dateFormat.format(record.getMillis()));

        builder.append(" [").append(record.getLevel().getName()).append("] ");

        // Add the Name of the Logger
        builder.append("[").append(record.getLoggerName()).append("] ");

        builder.append(this.formatMessage(record));
        builder.append('\n');
        Throwable exception = record.getThrown();

        if (exception != null) {
            StringWriter writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
        }

        return builder.toString();
    }
}
