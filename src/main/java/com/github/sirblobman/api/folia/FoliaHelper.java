package com.github.sirblobman.api.folia;

import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

import com.github.sirblobman.api.folia.scheduler.BukkitTaskScheduler;
import com.github.sirblobman.api.folia.scheduler.FoliaTaskScheduler;
import com.github.sirblobman.api.folia.scheduler.TaskScheduler;

public final class FoliaHelper<P extends Plugin> {
    private final P plugin;

    private Boolean foliaSupported;
    private TaskScheduler<P> scheduler;

    public FoliaHelper(@NotNull P plugin) {
        this.plugin = plugin;
        this.foliaSupported = null;
        this.scheduler = null;
    }

    private @NotNull P getPlugin() {
        return this.plugin;
    }

    private @NotNull Logger getLogger() {
        P plugin = getPlugin();
        return plugin.getLogger();
    }

    public boolean isFolia() {
        if (this.foliaSupported != null) {
            return this.foliaSupported;
        }

        try {
            String className = "io.papermc.paper.threadedregions.RegionizedServer";
            Class.forName(className);

            Logger logger = getLogger();
            logger.info("Detected server as Folia.");
            return (this.foliaSupported = true);
        } catch (ClassNotFoundException ex) {
            Logger logger = getLogger();
            logger.info("Detected server as regular SpigotMC/PaperMC (not Folia)");
            return (this.foliaSupported = false);
        }
    }

    public @NotNull TaskScheduler<P> getScheduler() {
        if (this.scheduler != null) {
            return this.scheduler;
        }

        P plugin = getPlugin();
        if (isFolia()) {
            this.scheduler = new FoliaTaskScheduler<>(plugin);
        } else {
            this.scheduler = new BukkitTaskScheduler<>(plugin);
        }

        return this.scheduler;
    }
}
