package com.github.sirblobman.api.folia.details;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

public final class RunnableTask<P extends Plugin> extends TaskDetails<P> {
    private final Runnable runnable;

    public RunnableTask(@NotNull P plugin, @NotNull Runnable runnable) {
        super(plugin);
        this.runnable = runnable;
    }

    private @NotNull Runnable getRunnable() {
        return this.runnable;
    }

    @Override
    public void run() {
        Runnable runnable = getRunnable();
        runnable.run();
    }
}
