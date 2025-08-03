package com.github.sirblobman.api.folia.teleport;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;

public final class FoliaTeleportHandler extends TeleportHandler {
    @Override
    public @NotNull CompletableFuture<Boolean> teleport(@NotNull Entity entity, @NotNull Location target) {
        return entity.teleportAsync(target, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
}
