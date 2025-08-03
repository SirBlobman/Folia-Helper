package com.github.sirblobman.api.folia;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

import com.github.sirblobman.api.folia.scheduler.TaskScheduler;
import com.github.sirblobman.api.folia.teleport.TeleportHandler;

public final class FoliaHelper {
    private final Plugin plugin;

    private Boolean foliaSupported;
    private TaskScheduler scheduler;
    private TeleportHandler teleporter;

    public FoliaHelper(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.foliaSupported = null;
        this.scheduler = null;
        this.teleporter = null;
    }

    private @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    private @NotNull Logger getLogger() {
        Plugin plugin = getPlugin();
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

    public @NotNull TaskScheduler getScheduler() {
        if (this.scheduler != null) {
            return this.scheduler;
        }

        String basePackage = getClass().getPackage().getName();
        String schedulerPackage = (basePackage + ".scheduler");
        String classSimpleName = (isFolia() ? "FoliaTaskScheduler" : "BukkitTaskScheduler");
        String className = (schedulerPackage + "." + classSimpleName);

        try {
            Class<?> schedulerClass = Class.forName(className);
            Constructor<?> constructor = schedulerClass.getConstructor(Plugin.class);

            Plugin plugin = getPlugin();
            Object instance = constructor.newInstance(plugin);
            this.scheduler = (TaskScheduler) instance;
            return this.scheduler;
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Missing class '" + className + "'.", ex);
        }
    }

    public @NotNull TeleportHandler getTeleporter() {
        if (this.teleporter != null) {
            return this.teleporter;
        }

        String basePackage = getClass().getPackage().getName();
        String schedulerPackage = (basePackage + ".teleport");
        String classSimpleName = (isFolia() ? "FoliaTeleportHandler" : "BukkitTeleportHandler");
        String className = (schedulerPackage + "." + classSimpleName);

        try {
            Class<?> teleporterClass = Class.forName(className);
            Constructor<?> constructor = teleporterClass.getConstructor();
            this.teleporter = (TeleportHandler) constructor.newInstance();
            return this.teleporter;
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Missing class '" + className + "'.", ex);
        }
    }
}
