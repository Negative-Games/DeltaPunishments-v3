package games.negative.punishments.core.structure;

import games.negative.punishments.api.model.PunishmentExecutable;
import games.negative.punishments.api.model.PunishmentType;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PunishmentExecutableImpl implements PunishmentExecutable {

    private final String key;
    private final PunishmentType type;
    private final String duration;
    private final List<String> commands;
    private final String permission;
    private final PunishmentExecutable parent;

    public PunishmentExecutableImpl(PunishmentExecutable parent, String key, ConfigurationSection section) {
        this.key = key;
        this.type = PunishmentType.valueOf(section.getString("type"));
        this.duration = section.getString("duration");
        this.commands = section.getStringList("commands");
        this.permission = section.getString("permission");
        this.parent = parent;
    }

    public PunishmentExecutableImpl(PunishmentExecutable parent, String key, PunishmentType type, String duration, String permission, List<String> commands) {
        this.key = key;
        this.type = type;
        this.duration = duration;
        this.commands = commands;
        this.permission = permission;
        this.parent = parent;
    }

    @Override
    public @Nullable PunishmentExecutable getParent() {
        return null;
    }

    @Override
    public @NotNull PunishmentType getType() {
        return type;
    }

    @Override
    public @NotNull String getDuration() {
        return duration;
    }

    @Override
    public @Nullable String getPermission() {
        return permission;
    }

    @Override
    public List<String> getCommands() {
        return commands;
    }

    @Override
    public @NotNull String getKey() {
        return key;
    }

    @Override
    public void setKey(@NotNull String s) {
        throw new UnsupportedOperationException("Cannot set key of punishment executable");
    }
}
