package com.github.sirblobman.api.folia.scheduler;

import org.jetbrains.annotations.NotNull;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import com.github.sirblobman.api.folia.details.EntityTaskDetails;
import com.github.sirblobman.api.folia.details.TaskDetails;
import com.github.sirblobman.api.folia.details.LocationTaskDetails;
import com.github.sirblobman.api.folia.task.WrappedTask;

public abstract class TaskScheduler<P extends Plugin> {
    private final P plugin;

    public TaskScheduler(@NotNull P plugin) {
        this.plugin = plugin;
    }

    protected final @NotNull P getPlugin() {
        return this.plugin;
    }

    protected abstract @NotNull WrappedTask scheduleTask(@NotNull TaskDetails<P> details);
    protected abstract @NotNull WrappedTask scheduleAsyncTask(@NotNull TaskDetails<P> details);
    protected abstract <E extends Entity> @NotNull WrappedTask scheduleEntityTask(EntityTaskDetails<P, E> details);
    protected abstract @NotNull WrappedTask scheduleLocationTask(LocationTaskDetails<P> details);
}
