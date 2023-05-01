package com.github.sirblobman.api.folia;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

public interface IFoliaPlugin {
    @NotNull Plugin getPlugin();
    @NotNull FoliaHelper getFoliaHelper();
}
