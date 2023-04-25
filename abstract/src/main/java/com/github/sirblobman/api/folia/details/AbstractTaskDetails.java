package com.github.sirblobman.api.folia.details;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.plugin.Plugin;

import com.github.sirblobman.api.folia.task.WrappedTask;

public abstract class AbstractTaskDetails<P extends Plugin> {
    private final P plugin;

    private Long delay;
    private Long period;

    private WrappedTask task;

    public AbstractTaskDetails(@NotNull P plugin) {
        this.plugin = plugin;
        this.delay = null;
        this.period = null;
        this.task = null;
    }

    public final @NotNull P getPlugin() {
        return this.plugin;
    }

    public final @Nullable Long getDelay() {
        return this.delay;
    }

    public final void setDelay(@Nullable Long delay) {
        this.delay = delay;
    }

    public final @Nullable Long getPeriod() {
        return this.period;
    }

    public final void setPeriod(@Nullable Long period) {
        this.period = period;
    }

    private @Nullable WrappedTask getTask() {
        return this.task;
    }

    public final void setTask(@NotNull WrappedTask task) {
        if (this.task != null) {
            throw new IllegalStateException("Already scheduled!");
        }

        this.task = task;
    }

    public final boolean isCancelled() {
        WrappedTask task = getTask();
        if (task == null) {
            return false;
        }

        return task.isCancelled();
    }

    public final void cancel() throws IllegalStateException {
        WrappedTask task = getTask();
        if (task == null) {
            throw new IllegalStateException("Not scheduled yet!");
        }

        task.cancel();
    }

    public abstract void run();
}
