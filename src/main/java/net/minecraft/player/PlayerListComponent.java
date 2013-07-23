package net.minecraft.player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.IUpdatePlayerListBox;

import javax.swing.*;
import java.util.Vector;

public class PlayerListComponent extends JList implements IUpdatePlayerListBox {
    private MinecraftServer field_120015_a;
    private int field_120014_b;

    public PlayerListComponent(MinecraftServer par1MinecraftServer) {
        this.field_120015_a = par1MinecraftServer;
        par1MinecraftServer.func_82010_a(this);
    }

    /**
     * Updates the JList with a new model.
     */
    public void update() {
        if (this.field_120014_b++ % 20 == 0) {
            Vector var1 = new Vector();

            for (int var2 = 0; var2 < this.field_120015_a.getConfigurationManager().playerEntityList.size(); ++var2) {
                var1.add((this.field_120015_a.getConfigurationManager().playerEntityList.get(var2)).getCommandSenderName());
            }

            this.setListData(var1);
        }
    }
}
