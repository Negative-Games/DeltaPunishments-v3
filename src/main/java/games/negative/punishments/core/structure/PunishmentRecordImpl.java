package games.negative.punishments.core.structure;

import games.negative.punishments.api.exception.InvalidUUIDException;
import games.negative.punishments.api.model.PunishmentRecord;
import games.negative.punishments.api.model.PunishmentType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@RequiredArgsConstructor
public class PunishmentRecordImpl implements PunishmentRecord {

    private final long key;
    private final UUID uuid;
    private final String reason;
    private final PunishmentType type;
    private final String staff;
    private final String remover;
    private final String removalReason;
    private final long time;
    private final long until;
    private final boolean active;

    @Override
    public @NotNull UUID getUniqueID() {
        return uuid;
    }

    @Override
    public @NotNull String getReason() {
        return reason;
    }

    @Override
    public @NotNull PunishmentType getType() {
        return type;
    }

    @Override
    public @Nullable String getStaff() {
        return staff;
    }

    @Override
    public @Nullable UUID getStaffUUID() throws InvalidUUIDException {
        try {
            return UUID.fromString(staff);
        } catch (Exception e) {
            throw new InvalidUUIDException("Could not parse '" + staff + "' into a UUID object.");
        }
    }

    @Override
    public @Nullable String getRemover() {
        return remover;
    }

    @Override
    public @Nullable UUID getRemoverUUID() throws InvalidUUIDException {
        try {
            return UUID.fromString(remover);
        } catch (Exception e) {
            throw new InvalidUUIDException("Could not parse '" + remover + "' into a UUID object.");
        }
    }

    @Override
    public @Nullable String getRemovalReason() {
        return removalReason;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public long getUntil() {
        return until;
    }

    @Override
    public boolean active() {
        return active;
    }

    @Override
    public @NotNull Long getKey() {
        return key;
    }

    @Override
    public void setKey(@NotNull Long value) {
        throw new UnsupportedOperationException("Cannot set the key of a punishment record.");
    }
}
