package com.github.sirblobman.api.folia.details;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public abstract class LocationTaskDetails extends AbstractTaskDetails {
    private final @NotNull Location location;

    public LocationTaskDetails(@NotNull Plugin plugin, @NotNull Location location) {
        super(plugin);

        World world = location.getWorld();
        Objects.requireNonNull(world, "location must have a non-null world!");

        this.location = location;
    }

    public final @NotNull Location getLocation() {
        return this.location.clone();
    }
}
