package com.github.sirblobman.api.folia;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

public interface IFoliaPlugin<P extends Plugin> {
    @NotNull P getPlugin();
    @NotNull FoliaHelper<P> getFoliaHelper();
}
