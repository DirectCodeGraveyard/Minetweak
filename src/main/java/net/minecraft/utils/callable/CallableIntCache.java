package net.minecraft.utils.callable;

import net.minecraft.crash.CrashReport;
import net.minecraft.src.IntCache;

import java.util.concurrent.Callable;

public class CallableIntCache implements Callable<String> {
    final CrashReport theCrashReport;

    public CallableIntCache(CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }

    public String func_85083_a() {
        return IntCache.func_85144_b();
    }

    @Override
    public String call() {
        return this.func_85083_a();
    }
}
