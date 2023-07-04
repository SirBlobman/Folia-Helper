package com.github.sirblobman.api.folia.task;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public final class WrappedBukkitTask extends WrappedTask {
    private static final boolean LEGACY_CANCEL;

    static {
        boolean legacy;
        try {
            Class<?> class_BukkitTask = Class.forName("org.bukkit.scheduler.BukkitTask");
            class_BukkitTask.getMethod("isCancelled");
            class_BukkitTask.getMethod("cancel");
            legacy = false;
        } catch (ReflectiveOperationException ex) {
            legacy = true;
        }

        LEGACY_CANCEL = legacy;
    }

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
        if (LEGACY_CANCEL) {
            cancelLegacy();
        } else {
            getTask().cancel();
        }
    }

    @Override
    public boolean isCancelled() {
        if (LEGACY_CANCEL) {
            return isCancelledLegacy();
        }

        return getTask().isCancelled();
    }

    private boolean isCancelledLegacy() {
        int taskId = getTask().getTaskId();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        return (!scheduler.isQueued(taskId) && !scheduler.isCurrentlyRunning(taskId));
    }

    private void cancelLegacy() {
        int taskId = getTask().getTaskId();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.cancelTask(taskId);
    }
}
