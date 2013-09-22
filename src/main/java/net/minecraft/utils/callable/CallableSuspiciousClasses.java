package net.minecraft.utils.callable;

import net.minecraft.crash.CrashReport;
import net.minecraft.src.ComparatorClassSorter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.Callable;

public class CallableSuspiciousClasses implements Callable<String> {
    final CrashReport theCrashReport;

    public CallableSuspiciousClasses(CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }

    public String callSuspiciousClasses() throws SecurityException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        StringBuilder var1 = new StringBuilder();
        ArrayList<Class> var3;

        try {
            Field var2 = ClassLoader.class.getDeclaredField("classes");
            var2.setAccessible(true);
            var3 = new ArrayList<Class>((Vector<Class>) var2.get(CrashReport.class.getClassLoader()));
        } catch (Exception ex) {
            return "";
        }

        boolean var4 = true;
        boolean var5 = !CrashReport.class.getCanonicalName().equals("net.minecraft.CrashReport");
        HashMap<String, Integer> var6 = new HashMap<String, Integer>();
        String var7 = "";
        Collections.sort(var3, new ComparatorClassSorter(this));

        for (Class c : var3) {

            if (c != null) {
                String var10 = c.getCanonicalName();

                if (var10 != null && !var10.startsWith("org.lwjgl.") && !var10.startsWith("paulscode.") && !var10.startsWith("org.bouncycastle.") && !var10.startsWith("argo.") && !var10.startsWith("com.jcraft.") && !var10.startsWith("com.fasterxml.") && !var10.startsWith("com.google.") && !var10.startsWith("joptsimple.") && !var10.startsWith("org.apache.") && !var10.equals("util.GLX")) {
                    if (var5) {
                        if (var10.length() <= 3 || var10.equals("net.minecraft.server.MinecraftServer")) {
                            continue;
                        }
                    } else if (var10.startsWith("net.minecraft")) {
                        continue;
                    }

                    Package var11 = c.getPackage();
                    String var12 = var11 == null ? "" : var11.getName();

                    if (var6.containsKey(var12)) {
                        int var13 = var6.get(var12);
                        var6.put(var12, var13 + 1);

                        if (var13 == 3) {
                            if (!var4) {
                                var1.append(", ");
                            }

                            var1.append("...");
                            var4 = false;
                            continue;
                        }

                        if (var13 > 3) {
                            continue;
                        }
                    } else {
                        var6.put(var12, 1);
                    }

                    if (!var7.equals(var12) && var7.length() > 0) {
                        var1.append("], ");
                    }

                    if (!var4 && var7.equals(var12)) {
                        var1.append(", ");
                    }

                    if (!var7.equals(var12)) {
                        var1.append("[");
                        var1.append(var12);
                        var1.append(".");
                    }

                    var1.append(c.getSimpleName());
                    var7 = var12;
                    var4 = false;
                }
            }
        }

        if (var4) {
            var1.append("No suspicious classes found.");
        } else {
            var1.append("]");
        }

        return var1.toString();
    }

    @Override
    public String call() {
        try {
            return this.callSuspiciousClasses();
        } catch (Exception ex) {
            return null;
        }
    }
}
