package net.minecraft.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogAgent implements ILogAgent {
    private final Logger serverLogger;
    private final String loggerPrefix;

    public LogAgent(String loggerName, String loggerPrefix) {
        this.serverLogger = Logger.getLogger(loggerName);
        this.loggerPrefix = loggerPrefix;
        this.setupLogger();
    }

    /**
     * Sets up the logger for usage.
     */
    private void setupLogger() {
        this.serverLogger.setUseParentHandlers(false);
        Handler[] var1 = this.serverLogger.getHandlers();

        for (Handler var4 : var1) {
            this.serverLogger.removeHandler(var4);
        }

        LogFormatter var6 = new LogFormatter(this);
        ConsoleHandler var7 = new ConsoleHandler();
        var7.setFormatter(var6);
        this.serverLogger.addHandler(var7);
    }

    @Override
    public Logger getLogger() {
        return this.serverLogger;
    }

    @Override
    public void logInfo(String par1Str) {
        this.serverLogger.log(Level.INFO, "[Minecraft] " + par1Str);
    }

    @Override
    public void logWarning(String par1Str) {
        this.serverLogger.log(Level.WARNING, "[Minecraft] " + par1Str);
    }

    @Override
    public void logWarningFormatted(String par1Str, Object... par2ArrayOfObj) {
        this.serverLogger.log(Level.WARNING, "[Minecraft] " + par1Str, par2ArrayOfObj);
    }

    @Override
    public void logWarningException(String par1Str, Throwable par2Throwable) {
        this.serverLogger.log(Level.WARNING, "[Minecraft] " + par1Str, par2Throwable);
    }

    @Override
    public void logSevere(String par1Str) {
        this.serverLogger.log(Level.SEVERE, "[Minecraft] " + par1Str);
    }

    @Override
    public void logSevereException(String par1Str, Throwable par2Throwable) {
        this.serverLogger.log(Level.SEVERE, "[Minecraft] " + par1Str, par2Throwable);
    }

    @Override
    public void logNoPrefix(String string) {
        this.serverLogger.log(Level.INFO, string);
    }

    static String getLogPrefix(LogAgent par0LogAgent) {
        return par0LogAgent.loggerPrefix;
    }
}
