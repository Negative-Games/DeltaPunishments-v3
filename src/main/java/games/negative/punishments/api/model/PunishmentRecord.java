package games.negative.punishments.api.model;

import games.negative.framework.key.Keyd;
import games.negative.punishments.api.exception.InvalidUUIDException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * This is a model class to represent a punishment record collected from LiteBans.
 */
public interface PunishmentRecord extends Keyd<Long> {

    /**
     * Gets the UUID of the player who was punished.
     * @return The UUID of the player who was punished.
     */
    @NotNull
    UUID getUniqueID();

    /**
     * Get the reason for the punishment.
     * @return The reason for the punishment.
     */
    @NotNull
    String getReason();

    /**
     * Gets the type of punishment.
     * @return The type of punishment.
     */
    @NotNull
    PunishmentType getType();

    /**
     * Gets the staff member who punished the player.
     * @return The staff member who punished the player.
     */
    @Nullable
    String getStaff();

    /**
     * Gets the UUID of the staff member who punished the player.
     * @return The UUID of the staff member who punished the player.
     * @throws InvalidUUIDException If the UUID is invalid, which would usually mean
     * the staff member is actually the console.
     */
    @Nullable
    UUID getStaffUUID() throws InvalidUUIDException;

    /**
     * Gets the staff member who removed the punishment.
     * @return The staff member who removed the punishment.
     */
    @Nullable
    String getRemover();

    /**
     * Gets the UUID of the staff member who removed the punishment.
     * @return The UUID of the staff member who removed the punishment.
     * @throws InvalidUUIDException If the UUID is invalid, which would usually mean
     * the staff member is actually the console.
     */
    @Nullable
    UUID getRemoverUUID() throws InvalidUUIDException;

    /**
     * Gets the reason for the removal of the punishment.
     * @return The reason for the removal of the punishment.
     */
    @Nullable
    String getRemovalReason();

    /**
     * Gets the time the punishment was issued.
     * @return The time the punishment was issued.
     */
    long getTime();

    /**
     * Gets the time the punishment will be removed.
     * @return The time the punishment will be removed.
     */
    long getUntil();

    /**
     * Checks if the punishment is active.
     * @return True if the punishment is active, false otherwise.
     */
    boolean active();
}
