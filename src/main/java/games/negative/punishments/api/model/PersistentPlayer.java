package games.negative.punishments.api.model;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * This is a model class to represent a player.
 */
public interface PersistentPlayer {

    @NotNull
    UUID getUniqueId();

    @NotNull
    String getName();

    /**
     * Wraps an OfflinePlayer into a PersistentPlayer.
     * @param player
     * @return a PersistentPlayer
     */
    static PersistentPlayer wrap(@NotNull OfflinePlayer player) {
        return new PersistentPlayer() {

            @Override
            public @NotNull UUID getUniqueId() {
                return player.getUniqueId();
            }

            @Override
            public @NotNull String getName() {
                return player.getName();
            }
        };
    }

    /**
     * Wraps a UUID and a tag into a PersistentPlayer.
     * @param uuid the UUID of the player
     * @param tag the tag of the player
     * @return a PersistentPlayer
     */
    static PersistentPlayer wrap(@NotNull UUID uuid, @NotNull String tag) {
        return new PersistentPlayer() {

            @Override
            public @NotNull UUID getUniqueId() {
                return uuid;
            }

            @Override
            public @NotNull String getName() {
                return tag;
            }
        };
    }
}
