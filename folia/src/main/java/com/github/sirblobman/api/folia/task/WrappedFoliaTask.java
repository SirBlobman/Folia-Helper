package com.github.sirblobman.api.folia.task;

import org.jetbrains.annotations.NotNull;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

public final class WrappedFoliaTask extends WrappedTask {
    private final ScheduledTask task;

    public WrappedFoliaTask(@NotNull ScheduledTask task) {
        super(task.getOwningPlugin());
        this.task = task;
    }

    private @NotNull ScheduledTask getTask() {
        return this.task;
    }

    @Override
    public void cancel() {
        ScheduledTask task = getTask();
        if (!task.isCancelled()) {
            task.cancel();
        }
    }

    @Override
    public boolean isCancelled() {
        ScheduledTask task = getTask();
        return task.isCancelled();
    }
}
