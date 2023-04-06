package games.negative.punishments.api;

import games.negative.punishments.api.model.PunishmentRecord;
import games.negative.punishments.api.model.PunishmentType;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

/**
 * Represents the class who holds the cache for all the punishment records
 * to allow the plugin to play with LiteBan's data.
 */
public interface PunishDataCache {

    void cache();

    void clearCache();

    BukkitTask getCacheTask();

    @NotNull
    Collection<PunishmentRecord> retrieveRecords(@NotNull UUID uuid, @Nullable PunishmentType type);

}
