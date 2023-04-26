package com.github.sirblobman.api.folia.scheduler;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import com.github.sirblobman.api.folia.details.AbstractTaskDetails;
import com.github.sirblobman.api.folia.details.EntityTaskDetails;
import com.github.sirblobman.api.folia.details.LocationTaskDetails;
import com.github.sirblobman.api.folia.details.TaskDetails;
import com.github.sirblobman.api.folia.task.WrappedBukkitTask;
import com.github.sirblobman.api.folia.task.WrappedTask;

public class BukkitTaskScheduler<P extends Plugin> extends TaskScheduler<P> {
    public BukkitTaskScheduler(@NotNull P plugin) {
        super(plugin);
    }

    @Override
    public @NotNull WrappedTask scheduleTask(@NotNull TaskDetails<P> details) {
        Long delay = details.getDelay();
        Long period = details.getPeriod();

        if (period != null) {
            long realDelay = (delay == null ? 1L : delay);
            return scheduleTaskTimer(details, realDelay, period);
        }

        if (delay != null) {
            return scheduleTaskDelayed(details, delay);
        }

        return scheduleTaskSingle(details);
    }

    @Override
    public @NotNull WrappedTask scheduleAsyncTask(@NotNull TaskDetails<P> details) {
        Long delay = details.getDelay();
        Long period = details.getPeriod();

        if (period != null) {
            long realDelay = (delay == null ? 1L : delay);
            return scheduleTaskTimerAsync(details, realDelay, period);
        }

        if (delay != null) {
            return scheduleTaskDelayedAsync(details, delay);
        }

        return scheduleTaskSingleAsync(details);
    }

    @Override
    public <E extends Entity> @NotNull WrappedTask scheduleEntityTask(EntityTaskDetails<P, E> details) {
        Long delay = details.getDelay();
        Long period = details.getPeriod();

        if (period != null) {
            long realDelay = (delay == null ? 1L : delay);
            return scheduleTaskTimer(details, realDelay, period);
        }

        if (delay != null) {
            return scheduleTaskDelayed(details, delay);
        }

        return scheduleTaskSingle(details);
    }

    @Override
    public @NotNull WrappedTask scheduleLocationTask(LocationTaskDetails<P> details) {
        Long delay = details.getDelay();
        Long period = details.getPeriod();

        if (period != null) {
            long realDelay = (delay == null ? 1L : delay);
            return scheduleTaskTimer(details, realDelay, period);
        }

        if (delay != null) {
            return scheduleTaskDelayed(details, delay);
        }

        return scheduleTaskSingle(details);
    }

    private @NotNull Server getServer() {
        P plugin = getPlugin();
        return plugin.getServer();
    }

    private @NotNull BukkitScheduler getScheduler() {
        Server server = getServer();
        return server.getScheduler();
    }

    private @NotNull WrappedTask wrap(@NotNull AbstractTaskDetails<P> details, @NotNull BukkitTask task) {
        WrappedTask wrapped = new WrappedBukkitTask(task);
        details.setTask(wrapped);
        return wrapped;
    }

    private @NotNull WrappedTask scheduleTaskTimer(@NotNull AbstractTaskDetails<P> details, long delay, long period) {
        P plugin = getPlugin();
        BukkitScheduler scheduler = getScheduler();
        BukkitTask scheduled = scheduler.runTaskTimer(plugin, details::run, delay, period);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskTimerAsync(@NotNull AbstractTaskDetails<P> details, long delay, long period) {
        P plugin = getPlugin();
        BukkitScheduler scheduler = getScheduler();
        BukkitTask scheduled = scheduler.runTaskTimerAsynchronously(plugin, details::run, delay, period);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskDelayed(@NotNull AbstractTaskDetails<P> details, long delay) {
        P plugin = getPlugin();
        BukkitScheduler scheduler = getScheduler();
        BukkitTask scheduled = scheduler.runTaskLater(plugin, details::run, delay);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskDelayedAsync(@NotNull AbstractTaskDetails<P> details, long delay) {
        P plugin = getPlugin();
        BukkitScheduler scheduler = getScheduler();
        BukkitTask scheduled = scheduler.runTaskLaterAsynchronously(plugin, details::run, delay);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskSingle(@NotNull AbstractTaskDetails<P> details) {
        P plugin = getPlugin();
        BukkitScheduler scheduler = getScheduler();
        BukkitTask scheduled = scheduler.runTask(plugin, details::run);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskSingleAsync(@NotNull AbstractTaskDetails<P> details) {
        P plugin = getPlugin();
        BukkitScheduler scheduler = getScheduler();
        BukkitTask scheduled = scheduler.runTaskAsynchronously(plugin, details::run);
        return wrap(details, scheduled);
    }
}
