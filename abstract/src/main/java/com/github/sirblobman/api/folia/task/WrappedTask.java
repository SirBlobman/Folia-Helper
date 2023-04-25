package com.github.sirblobman.api.folia.task;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

public abstract class WrappedTask {
    private final Plugin plugin;

    public WrappedTask(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    protected final @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    public abstract void cancel();
    public abstract boolean isCancelled();
}
