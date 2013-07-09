package net.minecraft.src;

import java.util.logging.*;

public class LogAgent implements ILogAgent {
    private final Logger serverLogger;
    private final String logFile;
    private final String loggerName;
    private final String loggerPrefix;

    public LogAgent(String par1Str, String par2Str, String par3Str) {
        this.serverLogger = Logger.getLogger(par1Str);
        this.loggerName = par1Str;
        this.loggerPrefix = par2Str;
        this.logFile = par3Str;
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

        LogFormatter var6 = new LogFormatter(this, null);
        ConsoleHandler var7 = new ConsoleHandler();
        var7.setFormatter(var6);
        this.serverLogger.addHandler(var7);

        try {
            FileHandler var8 = new FileHandler(this.logFile, true);
            var8.setFormatter(var6);
            this.serverLogger.addHandler(var8);
        } catch (Exception var5) {
            this.serverLogger.log(Level.WARNING, "Failed to log " + this.loggerName + " to " + this.logFile, var5);
        }
    }

    public Logger getLogger() {
        return this.serverLogger;
    }

    public void logInfo(String par1Str) {
        this.serverLogger.log(Level.INFO, "[Minecraft] " + par1Str);
    }

    public void logWarning(String par1Str) {
        this.serverLogger.log(Level.WARNING, "[Minecraft] " + par1Str);
    }

    public void logWarningFormatted(String par1Str, Object... par2ArrayOfObj) {
        this.serverLogger.log(Level.WARNING, "[Minecraft] " + par1Str, par2ArrayOfObj);
    }

    public void logWarningException(String par1Str, Throwable par2Throwable) {
        this.serverLogger.log(Level.WARNING, "[Minecraft] " + par1Str, par2Throwable);
    }

    public void logSevere(String par1Str) {
        this.serverLogger.log(Level.SEVERE, "[Minecraft] " + par1Str);
    }

    public void logSevereException(String par1Str, Throwable par2Throwable) {
        this.serverLogger.log(Level.SEVERE, "[Minecraft] " + par1Str, par2Throwable);
    }

    public void logNoPrefix(String string) {
        this.serverLogger.log(Level.INFO, string);
    }

    static String getLogPrefix(LogAgent par0LogAgent) {
        return par0LogAgent.loggerPrefix;
    }
}
