package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import org.minetweak.chat.TextColor;

import java.text.DecimalFormat;

public class CommandTps extends CommandExecutor {
    private static final DecimalFormat DF = new DecimalFormat("########0.000");

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        String tps = getTPS(MinecraftServer.getServer().tickTimeArray);
        if (tps.equals("20")) {
            tps = TextColor.GREEN + tps;
        } else {
            tps = TextColor.YELLOW + tps;
        }
        sender.sendMessage(TextColor.GOLD + "Server is running at" + TextColor.RESET + " " + tps + TextColor.GOLD + " TPS.");
    }

    public static String getTPS(long[] tickArray) {
        double tps = calcTicksInSecond(tickArray);
        if (tps < 50)
            return "20";
        else
            return DF.format(1000 / tps);
    }

    private static double calcTicksInSecond(long[] tickArray) {
        long var2 = 0L;

        for (long var7 : tickArray) {
            var2 += var7;
        }

        return (double) var2 / (double) tickArray.length * 1.0E-6D;
    }

    @Override
    public String getHelpInfo() {
        return "Gets the Ticks Per Second of the Server";
    }
}
