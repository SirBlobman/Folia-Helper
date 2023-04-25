package com.github.sirblobman.api.folia.task;

import org.jetbrains.annotations.NotNull;

import org.bukkit.scheduler.BukkitTask;

public final class WrappedBukkitTask extends WrappedTask {
    private final BukkitTask task;

    public WrappedBukkitTask(@NotNull BukkitTask task) {
        super(task.getOwner());
        this.task = task;
    }

    private @NotNull BukkitTask getTask() {
        return this.task;
    }

    @Override
    public void cancel() {
        BukkitTask task = getTask();
        if (!task.isCancelled()) {
            task.cancel();
        }
    }

    @Override
    public boolean isCancelled() {
        BukkitTask task = getTask();
        return task.isCancelled();
    }
}
