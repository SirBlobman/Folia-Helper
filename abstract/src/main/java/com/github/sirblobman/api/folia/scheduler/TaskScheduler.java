package com.github.sirblobman.api.folia.scheduler;

import org.jetbrains.annotations.NotNull;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import com.github.sirblobman.api.folia.details.EntityTaskDetails;
import com.github.sirblobman.api.folia.details.TaskDetails;
import com.github.sirblobman.api.folia.details.LocationTaskDetails;
import com.github.sirblobman.api.folia.task.WrappedTask;

public abstract class TaskScheduler {
    private final Plugin plugin;

    public TaskScheduler(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    protected final @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    public abstract @NotNull WrappedTask scheduleTask(@NotNull TaskDetails details);
    public abstract @NotNull WrappedTask scheduleAsyncTask(@NotNull TaskDetails details);
    public abstract <E extends Entity> @NotNull WrappedTask scheduleEntityTask(EntityTaskDetails<E> details);
    public abstract @NotNull WrappedTask scheduleLocationTask(LocationTaskDetails details);
}
