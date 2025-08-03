package com.github.sirblobman.api.folia.teleport;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public abstract class TeleportHandler {
    public abstract @NotNull CompletableFuture<Boolean> teleport(@NotNull Entity entity, @NotNull Location target);

    public @NotNull CompletableFuture<Boolean> teleport(@NotNull Entity entity, @NotNull Entity target) {
        Location location = target.getLocation();
        return teleport(entity, location);
    }
}
