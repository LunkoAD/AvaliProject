package com.lunkoashtail.avaliproject.block.custom;

import net.minecraft.util.StringRepresentable;

public enum ShellType implements StringRepresentable {
    CLAM("clam"),
    CONCH("conch"),
    COWRIE("cowrie");

    private final String name;

    ShellType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
