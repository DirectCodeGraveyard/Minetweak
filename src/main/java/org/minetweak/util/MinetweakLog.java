package org.minetweak.util;

import net.minecraft.logging.ILogAgent;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinetweakLog implements ILogAgent {
    private final Logger serverLogger;
    private final String prefix;

    public MinetweakLog(String loggerName, String prefix) {
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
        this.serverLogger.log(Level.INFO, "[Minetweak] " + line);
    }

    @Override
    public void logWarning(String line) {
        this.serverLogger.log(Level.WARNING, "[Minetweak] " + line);
    }

    @Override
    public void logWarningFormatted(String line, Object... objects) {
        this.serverLogger.log(Level.WARNING, "[Minetweak] " + line, objects);
    }

    @Override
    public void logWarningException(String line, Throwable exception) {
        this.serverLogger.log(Level.WARNING, "[Minetweak] " + line, exception);
    }

    @Override
    public void logSevere(String line) {
        this.serverLogger.log(Level.SEVERE, "[Minetweak] " + line);
    }

    @Override
    public void logSevereException(String line, Throwable exception) {
        this.serverLogger.log(Level.SEVERE, "[Minetweak] " + line, exception);
    }

    @Override
    public void logNoPrefix(String line) {
        this.serverLogger.log(Level.INFO, line);
    }

    public static String getLogPrefix(MinetweakLog logger) {
        return logger.prefix;
    }
}