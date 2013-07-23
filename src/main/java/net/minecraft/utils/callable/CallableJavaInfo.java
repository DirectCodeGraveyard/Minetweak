package net.minecraft.utils.callable;

import net.minecraft.crash.CrashReport;

import java.util.concurrent.Callable;

public class CallableJavaInfo implements Callable<String> {
    /**
     * Reference to the CrashReport object.
     */
    final CrashReport theCrashReport;

    public CallableJavaInfo(CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }

    /**
     * Returns the Java VM Information as a String.  Includes the Version and Vender.
     */
    public String getJavaInfoAsString() {
        return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
    }

    @Override
    public String call() {
        return this.getJavaInfoAsString();
    }
}
