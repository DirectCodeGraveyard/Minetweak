package org.minetweak.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    private SimpleDateFormat dateFormat;

    final MinetweakLog logger;

    protected LogFormatter(MinetweakLog logger) {
        this.logger = logger;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.dateFormat.format(record.getMillis()));

        if (MinetweakLog.getLogPrefix(this.logger) != null) {
            builder.append(MinetweakLog.getLogPrefix(this.logger));
        }

        builder.append(" [").append(record.getLevel().getName()).append("] ");
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