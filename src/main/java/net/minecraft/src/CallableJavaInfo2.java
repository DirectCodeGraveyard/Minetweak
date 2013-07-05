package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableJavaInfo2 implements Callable<String> {
    /**
     * Reference to the CrashReport object.
     */
    final CrashReport theCrashReport;

    CallableJavaInfo2(CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }

    /**
     * Retuns the Java VM Information as a String.  Includes the VM Name, VM Info and VM Vendor.
     */
    public String getJavaVMInfoAsString() {
        return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
    }

    @Override
    public String call() {
        return this.getJavaVMInfoAsString();
    }
}
