package net.minecraft.crash.exception;

import net.minecraft.command.CommandException;

public class NumberInvalidException extends CommandException {
    public NumberInvalidException() {
        this("commands.generic.num.invalid", new Object[0]);
    }

    public NumberInvalidException(String par1Str, Object... par2ArrayOfObj) {
        super(par1Str, par2ArrayOfObj);
    }
}
