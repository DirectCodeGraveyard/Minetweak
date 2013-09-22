package net.minecraft.utils.callable;

import net.minecraft.crash.CrashReport;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.concurrent.Callable;

public class CallableJVMFlags implements Callable<String> {
    /**
     * Reference to the CrashReport object.
     */
    final CrashReport theCrashReport;

    public CallableJVMFlags(CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }

    /**
     * Returns the number of JVM Flags along with the passed JVM Flags.
     */
    public String getJVMFlagsAsString() {
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        List<String> var2 = bean.getInputArguments();
        int var3 = 0;
        StringBuilder var4 = new StringBuilder();

        for (String opt : var2) {

            if (opt.startsWith("-X")) {
                if (var3++ > 0) {
                    var4.append(" ");
                }

                var4.append(opt);
            }
        }

        return String.format("%d total; %s", var3, var4.toString());
    }

    @Override
    public String call() {
        return this.getJVMFlagsAsString();
    }
}
