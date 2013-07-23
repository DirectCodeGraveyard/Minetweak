package net.minecraft.utils.callable;

import net.minecraft.crash.CrashReport;

import java.util.concurrent.Callable;

public class CallableOSInfo implements Callable<String> {
    /**
     * Reference to the CrashReport object.
     */
    final CrashReport theCrashReport;

    public CallableOSInfo(CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }

    public String getOsAsString() {
        return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
    }

    @Override
    public String call() {
        return this.getOsAsString();
    }
}
