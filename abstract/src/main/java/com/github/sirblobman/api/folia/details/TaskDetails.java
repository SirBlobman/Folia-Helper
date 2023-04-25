package com.github.sirblobman.api.folia.details;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

public abstract class TaskDetails<P extends Plugin> extends AbstractTaskDetails<P> {
    public TaskDetails(@NotNull P plugin) {
        super(plugin);
    }
}
