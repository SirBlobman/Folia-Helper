package com.github.sirblobman.api.folia.scheduler;

import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

import com.github.sirblobman.api.folia.details.AbstractTaskDetails;
import com.github.sirblobman.api.folia.details.EntityTaskDetails;
import com.github.sirblobman.api.folia.details.LocationTaskDetails;
import com.github.sirblobman.api.folia.details.TaskDetails;
import com.github.sirblobman.api.folia.task.WrappedFoliaTask;
import com.github.sirblobman.api.folia.task.WrappedTask;

public class FoliaTaskScheduler<P extends Plugin> extends TaskScheduler<P> {
    public FoliaTaskScheduler(@NotNull P plugin) {
        super(plugin);
    }

    @Override
    protected @NotNull WrappedTask scheduleTask(@NotNull TaskDetails<P> details) {
        Long delay = details.getDelay();
        Long period = details.getPeriod();

        if (period != null) {
            long realDelay = (delay == null ? 0L : delay);
            return scheduleTaskTimer(details, realDelay, period);
        }

        if (delay != null) {
            return scheduleTaskDelayed(details, delay);
        }

        return scheduleTaskSingle(details);
    }

    @Override
    protected @NotNull WrappedTask scheduleAsyncTask(@NotNull TaskDetails<P> details) {
        Long delay = details.getDelay();
        Long period = details.getPeriod();

        if (period != null) {
            long realDelay = (delay == null ? 0L : delay);
            return scheduleTaskTimerAsync(details, realDelay, period);
        }

        if (delay != null) {
            return scheduleTaskDelayedAsync(details, delay);
        }

        return scheduleTaskSingleAsync(details);
    }

    @Override
    protected @NotNull <E extends Entity> WrappedTask scheduleEntityTask(EntityTaskDetails<P, E> details) {
        Long delay = details.getDelay();
        Long period = details.getPeriod();

        if (period != null) {
            long realDelay = (delay == null ? 0L : delay);
            return scheduleTaskTimer(details, realDelay, period);
        }

        if (delay != null) {
            return scheduleTaskDelayed(details, delay);
        }

        return scheduleTaskSingle(details);
    }

    @Override
    protected @NotNull WrappedTask scheduleLocationTask(LocationTaskDetails<P> details) {
        Long delay = details.getDelay();
        Long period = details.getPeriod();

        if (period != null) {
            long realDelay = (delay == null ? 0L : delay);
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

    private @NotNull GlobalRegionScheduler getGlobalRegionScheduler() {
        Server server = getServer();
        return server.getGlobalRegionScheduler();
    }

    private @NotNull AsyncScheduler getAsyncScheduler() {
        Server server = getServer();
        return server.getAsyncScheduler();
    }

    private @NotNull RegionScheduler getRegionScheduler() {
        Server server = getServer();
        return server.getRegionScheduler();
    }

    private @NotNull <E extends Entity> EntityScheduler getEntityScheduler(@NotNull E entity) {
        return entity.getScheduler();
    }

    private @NotNull WrappedTask wrap(@NotNull AbstractTaskDetails<P> details, @NotNull ScheduledTask task) {
        WrappedTask wrapped = new WrappedFoliaTask(task);
        details.setTask(wrapped);
        return wrapped;
    }

    private @NotNull WrappedTask scheduleTaskTimer(@NotNull TaskDetails<P> details, long delay, long period) {
        P plugin = getPlugin();
        GlobalRegionScheduler scheduler = getGlobalRegionScheduler();
        ScheduledTask scheduled = scheduler.runAtFixedRate(plugin, unused -> details.run(), delay, period);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskDelayed(@NotNull TaskDetails<P> details, long delay) {
        P plugin = getPlugin();
        GlobalRegionScheduler scheduler = getGlobalRegionScheduler();
        ScheduledTask scheduled = scheduler.runDelayed(plugin, unused -> details.run(), delay);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskSingle(@NotNull TaskDetails<P> details) {
        P plugin = getPlugin();
        GlobalRegionScheduler scheduler = getGlobalRegionScheduler();
        ScheduledTask scheduled = scheduler.run(plugin, unused -> details.run());
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskTimerAsync(@NotNull TaskDetails<P> details, long delay, long period) {
        P plugin = getPlugin();
        AsyncScheduler scheduler = getAsyncScheduler();

        long delayMillis = (delay * 50L);
        long periodMillis = (period * 50L);
        ScheduledTask scheduled = scheduler.runAtFixedRate(plugin, unused -> details.run(),
                delayMillis, periodMillis, TimeUnit.MILLISECONDS);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskDelayedAsync(@NotNull TaskDetails<P> details, long delay) {
        P plugin = getPlugin();
        AsyncScheduler scheduler = getAsyncScheduler();

        long delayMillis = (delay * 50L);
        ScheduledTask scheduled = scheduler.runDelayed(plugin, unused -> details.run(),
                delayMillis, TimeUnit.MILLISECONDS);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskSingleAsync(@NotNull TaskDetails<P> details) {
        P plugin = getPlugin();
        AsyncScheduler scheduler = getAsyncScheduler();
        ScheduledTask scheduled = scheduler.runNow(plugin, unused -> details.run());
        return wrap(details, scheduled);
    }

    private @NotNull <E extends Entity> WrappedTask scheduleTaskTimer(@NotNull EntityTaskDetails<P, E> details,
                                                                      long delay, long period) {
        P plugin = getPlugin();
        E entity = details.getEntity();
        if (entity == null) {
            throw new IllegalStateException("The entity for this task has already been removed.");
        }

        EntityScheduler scheduler = getEntityScheduler(entity);
        ScheduledTask scheduled = scheduler.runAtFixedRate(plugin, unused -> details.run(), null, delay, period);
        if (scheduled == null) {
            throw new IllegalStateException("The entity for this task has already been removed.");
        }

        return wrap(details, scheduled);
    }

    private @NotNull <E extends Entity> WrappedTask scheduleTaskDelayed(@NotNull EntityTaskDetails<P, E> details,
                                                                        long delay) {
        P plugin = getPlugin();
        E entity = details.getEntity();
        if (entity == null) {
            throw new IllegalStateException("The entity for this task has already been removed.");
        }

        EntityScheduler scheduler = getEntityScheduler(entity);
        ScheduledTask scheduled = scheduler.runDelayed(plugin, unused -> details.run(), null, delay);
        if (scheduled == null) {
            throw new IllegalStateException("The entity for this task has already been removed.");
        }

        return wrap(details, scheduled);
    }


    private @NotNull <E extends Entity> WrappedTask scheduleTaskSingle(@NotNull EntityTaskDetails<P, E> details) {
        P plugin = getPlugin();
        E entity = details.getEntity();
        if (entity == null) {
            throw new IllegalStateException("The entity for this task has already been removed.");
        }

        EntityScheduler scheduler = getEntityScheduler(entity);
        ScheduledTask scheduled = scheduler.run(plugin, unused -> details.run(), null);
        if (scheduled == null) {
            throw new IllegalStateException("The entity for this task has already been removed.");
        }

        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskTimer(@NotNull LocationTaskDetails<P> details, long delay, long period) {
        P plugin = getPlugin();
        Location location = details.getLocation();
        RegionScheduler scheduler = getRegionScheduler();
        ScheduledTask scheduled = scheduler.runAtFixedRate(plugin, location, unused -> details.run(), delay, period);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskDelayed(@NotNull LocationTaskDetails<P> details, long delay) {
        P plugin = getPlugin();
        Location location = details.getLocation();
        RegionScheduler scheduler = getRegionScheduler();
        ScheduledTask scheduled = scheduler.runDelayed(plugin, location, unused -> details.run(), delay);
        return wrap(details, scheduled);
    }

    private @NotNull WrappedTask scheduleTaskSingle(@NotNull LocationTaskDetails<P> details) {
        P plugin = getPlugin();
        Location location = details.getLocation();
        RegionScheduler scheduler = getRegionScheduler();
        ScheduledTask scheduled = scheduler.run(plugin, location, unused -> details.run());
        return wrap(details, scheduled);
    }
}
