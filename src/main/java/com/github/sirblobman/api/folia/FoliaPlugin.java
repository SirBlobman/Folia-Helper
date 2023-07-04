package com.github.sirblobman.api.folia;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

public interface FoliaPlugin {
    @NotNull Plugin getPlugin();
    @NotNull FoliaHelper getFoliaHelper();
}
