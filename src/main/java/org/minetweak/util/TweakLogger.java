package org.minetweak.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base Logger for Minetweak Operations
 */
public class TweakLogger {
    private final Logger serverLogger;
    private final String prefix;

    public TweakLogger(String loggerName) {
        this.serverLogger = Logger.getLogger(loggerName);
        this.prefix = loggerName;
        this.setupLogger();
    }

    /**
     * Sets up the logger for usage.
     */
    private void setupLogger() {
        this.serverLogger.setUseParentHandlers(false);
        Handler[] handlers = this.serverLogger.getHandlers();

        for (Handler handler : handlers) {
            this.serverLogger.removeHandler(handler);
        }

        LogFormatter formatter = new LogFormatter(this);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        this.serverLogger.addHandler(consoleHandler);
    }

    public Logger getLogger() {
        return this.serverLogger;
    }

    /**
     * Logs info to the Console
     *
     * @param line line to log
     */
    public void info(String line) {
        this.serverLogger.log(Level.INFO, "[" + prefix + "] " + line);
    }

    /**
     * Logs warning to the Console
     *
     * @param line line to log
     */
    public void logWarning(String line) {
        this.serverLogger.log(Level.WARNING, "[" + prefix + "] " + line);
    }

    /**
     * Logs warning that is formatted to the Console
     *
     * @param line line to log
     */
    public void logWarningFormatted(String line, Object... objects) {
        this.serverLogger.log(Level.WARNING, "[" + prefix + "] " + line, objects);
    }

    /**
     * Logs warning with an exception to the Console
     *
     * @param line line to log
     */
    public void logWarningException(String line, Throwable exception) {
        this.serverLogger.log(Level.WARNING, "[" + prefix + "] " + line, exception);
    }

    /**
     * Logs severe to the Console
     *
     * @param line line to log
     */
    public void logSevere(String line) {
        this.serverLogger.log(Level.SEVERE, "[" + prefix + "] " + line);
    }

    /**
     * Logs severe with an exception to the Console
     *
     * @param line line to log
     */
    public void logSevereException(String line, Throwable exception) {
        this.serverLogger.log(Level.SEVERE, "[" + prefix + "] " + line, exception);
    }

    /**
     * Logs info without a prefix to the Console
     *
     * @param line line to log
     */
    public void logNoPrefix(String line) {
        this.serverLogger.log(Level.INFO, line);
    }

    /**
     * Gets the Loggers prefixes
     */
    public static String getPrefix(TweakLogger logger) {
        return logger.prefix;
    }
}