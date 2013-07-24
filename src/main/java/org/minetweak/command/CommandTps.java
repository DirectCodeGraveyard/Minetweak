package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.enums.EnumChatFormatting;

import java.text.DecimalFormat;

public class CommandTps extends CommandExecutor {
    private static final DecimalFormat	DF	= new DecimalFormat("########0.000");
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        String tps = getTPS(MinecraftServer.getServer().tickTimeArray);
        if (tps.equals("20")) {
            tps = EnumChatFormatting.GREEN + tps;
        } else {
            tps = EnumChatFormatting.YELLOW + tps;
        }
        sender.sendMessage(EnumChatFormatting.GOLD + "TPS" + EnumChatFormatting.RESET + ": " + tps);
    }

    public String getTPS(long[] tickArray) {
        double tps = calcTicksInSecond(tickArray);
        if (tps < 50)
            return "20";
        else
            return DF.format(1000 / tps);
    }

    private double calcTicksInSecond(long[] tickArray) {
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
