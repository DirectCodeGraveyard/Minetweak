package org.minetweak.util;

import net.minecraft.logging.ILogAgent;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base Logger for Minetweak Operations
 */
public class TweakLogger implements ILogAgent {
    private final Logger serverLogger;
    private final String prefix;

    public TweakLogger(String loggerName, String prefix) {
        this.serverLogger = Logger.getLogger(loggerName);
        this.prefix = prefix;
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

    @Override
    public Logger getLogger() {
        return this.serverLogger;
    }

    @Override
    public void logInfo(String line) {
        this.serverLogger.log(Level.INFO, "[" + prefix + "] " + line);
    }

    @Override
    public void logWarning(String line) {
        this.serverLogger.log(Level.WARNING, "[" + prefix + "] " + line);
    }

    @Override
    public void logWarningFormatted(String line, Object... objects) {
        this.serverLogger.log(Level.WARNING, "[" + prefix + "] " + line, objects);
    }

    @Override
    public void logWarningException(String line, Throwable exception) {
        this.serverLogger.log(Level.WARNING, "[" + prefix + "] " + line, exception);
    }

    @Override
    public void logSevere(String line) {
        this.serverLogger.log(Level.SEVERE, "[" + prefix + "] " + line);
    }

    @Override
    public void logSevereException(String line, Throwable exception) {
        this.serverLogger.log(Level.SEVERE, "[" + prefix + "] " + line, exception);
    }

    @Override
    public void logNoPrefix(String line) {
        this.serverLogger.log(Level.INFO, line);
    }

    public static String getLogPrefix(TweakLogger logger) {
        return logger.prefix;
    }
}