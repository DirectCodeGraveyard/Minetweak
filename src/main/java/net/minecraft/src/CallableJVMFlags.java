package net.minecraft.src;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.concurrent.Callable;

class CallableJVMFlags implements Callable<String> {
    /**
     * Reference to the CrashReport object.
     */
    final CrashReport theCrashReport;

    CallableJVMFlags(CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }

    /**
     * Returns the number of JVM Flags along with the passed JVM Flags.
     */
    public String getJVMFlagsAsString() {
        RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        List var2 = var1.getInputArguments();
        int var3 = 0;
        StringBuilder var4 = new StringBuilder();

        for (Object aVar2 : var2) {
            String var6 = (String) aVar2;

            if (var6.startsWith("-X")) {
                if (var3++ > 0) {
                    var4.append(" ");
                }

                var4.append(var6);
            }
        }

        return String.format("%d total; %s", var3, var4.toString());
    }

    @Override
    public String call() {
        return this.getJVMFlagsAsString();
    }
}
