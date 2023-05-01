package com.github.sirblobman.api.folia.details;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

public abstract class TaskDetails extends AbstractTaskDetails {
    public TaskDetails(@NotNull Plugin plugin) {
        super(plugin);
    }
}
