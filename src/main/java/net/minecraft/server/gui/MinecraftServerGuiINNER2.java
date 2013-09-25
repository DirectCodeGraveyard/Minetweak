package net.minecraft.server.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MinecraftServerGuiINNER2 implements ActionListener {
    final JTextField field_120025_a;

    final MinecraftServerGui field_120024_b;

    MinecraftServerGuiINNER2(MinecraftServerGui par1MinecraftServerGui, JTextField par2JTextField) {
        this.field_120024_b = par1MinecraftServerGui;
        this.field_120025_a = par2JTextField;
    }

    public void actionPerformed(ActionEvent par1ActionEvent) {
        String var2 = this.field_120025_a.getText().trim();

        if (var2.length() > 0) {
            MinecraftServerGui.func_120017_a(this.field_120024_b).addPendingCommand(var2);
        }

        this.field_120025_a.setText("");
    }
}
