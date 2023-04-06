package games.negative.punishments.api;

import games.negative.punishments.api.model.Punishment;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Represents the management class for the plugin.
 */
public interface PunishManager {

    /**
     * Finds a punishment by its key.
     * @param key The key of the punishment.
     * @return The punishment if found, null otherwise.
     */
    @Nullable
    Punishment findPunishment(@NotNull String key);

    /**
     * Gets all the punishments.
     * @return A map of all the punishments.
     */
    Map<String, Punishment> getPunishments();

    /**
     * Reload function for things that need to be reloaded.
     */
    void reload();

    /**
     * Gets the cache for the punishment records.
     * @return The cache.
     */
    @NotNull
    PunishDataCache getCache();

    void executePunishment(CommandSender staff, OfflinePlayer offender, Punishment punishment, boolean silent, boolean skipToFinal);
}
