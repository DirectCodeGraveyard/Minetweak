package org.minetweak.util;

import org.minetweak.Start;

import java.io.IOException;
import java.util.logging.*;

/**
 * Base Logger for Minetweak Operations
 */
public class TweakLogger {
    private final Logger logger;
    private final String prefix;

    /**
     * Makes a Logger Instance
     *
     * @param loggerName Name of Logger
     */
    public TweakLogger(String loggerName) {
        this.logger = Logger.getLogger(loggerName);
        this.prefix = loggerName;
        this.setupLogger();
    }

    /**
     * Sets up the logger for usage.
     */
    private void setupLogger() {
        this.logger.setUseParentHandlers(false);
        Handler[] handlers = this.logger.getHandlers();

        for (Handler handler : handlers) {
            this.logger.removeHandler(handler);
        }

        LogFormatter formatter = new LogFormatter(this);

        if (Start.getInstance() != null && !Start.getInstance().isQuiet()) {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(formatter);
            this.logger.addHandler(consoleHandler);
        }

        try {
            FileHandler fileHandler = new FileHandler("minetweak.log", true);
            fileHandler.setFormatter(formatter);
            this.logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the Java Logger
     *
     * @return logger
     */
    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Logs info to the Console
     *
     * @param line line to log
     */
    public void info(String line) {
        this.logger.log(Level.INFO, line);
    }

    /**
     * Logs warning to the Console
     *
     * @param line line to log
     */
    public void logWarning(String line) {
        this.logger.log(Level.WARNING, line);
    }

    /**
     * Logs warning that is formatted to the Console
     *
     * @param line line to log
     */
    public void logWarningFormatted(String line, Object... objects) {
        this.logger.log(Level.WARNING, line, objects);
    }

    /**
     * Logs warning with an exception to the Console
     *
     * @param line line to log
     */
    public void logWarningException(String line, Throwable exception) {
        this.logger.log(Level.WARNING, line, exception);
    }

    /**
     * Logs severe to the Console
     *
     * @param line line to log
     */
    public void logSevere(String line) {
        this.logger.log(Level.SEVERE, line);
    }

    /**
     * Logs severe with an exception to the Console
     *
     * @param line line to log
     */
    public void logSevereException(String line, Throwable exception) {
        this.logger.log(Level.SEVERE, line, exception);
    }

    /**
     * Gets the Loggers prefixes
     */
    public static String getPrefix(TweakLogger logger) {
        return logger.prefix;
    }
}