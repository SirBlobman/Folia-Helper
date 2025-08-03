package com.github.sirblobman.api.folia.teleport;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public final class BukkitTeleportHandler extends TeleportHandler {
    @Override
    public @NotNull CompletableFuture<Boolean> teleport(@NotNull Entity entity, @NotNull Location target) {
        boolean teleport = entity.teleport(target);
        return CompletableFuture.completedFuture(teleport);
    }
}
