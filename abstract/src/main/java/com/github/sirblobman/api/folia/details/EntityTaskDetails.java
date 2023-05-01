package com.github.sirblobman.api.folia.details;

import java.lang.ref.WeakReference;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public abstract class EntityTaskDetails<E extends Entity> extends AbstractTaskDetails {
    private final WeakReference<E> entityReference;

    public EntityTaskDetails(@NotNull Plugin plugin, @NotNull E entity) {
        super(plugin);
        this.entityReference = new WeakReference<>(entity);
    }

    protected final @NotNull WeakReference<E> getEntityReference() {
        return this.entityReference;
    }

    public final @Nullable E getEntity() {
        WeakReference<E> entityReference = getEntityReference();
        return entityReference.get();
    }
}
