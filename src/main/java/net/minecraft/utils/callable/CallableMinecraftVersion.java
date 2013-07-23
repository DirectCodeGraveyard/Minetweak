package net.minecraft.utils.callable;

import net.minecraft.crash.CrashReport;

import java.util.concurrent.Callable;

public class CallableMinecraftVersion implements Callable<String> {
    /**
     * Reference to the CrashReport object.
     */
    final CrashReport theCrashReport;

    public CallableMinecraftVersion(CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }

    /**
     * The current version of Minecraft
     */
    public String minecraftVersion() {
        return "1.6.2";
    }

    @Override
    public String call() {
        return this.minecraftVersion();
    }
}
