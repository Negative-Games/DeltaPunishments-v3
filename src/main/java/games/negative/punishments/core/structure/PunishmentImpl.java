package games.negative.punishments.core.structure;

import com.google.common.collect.Lists;
import games.negative.punishments.api.model.Punishment;
import games.negative.punishments.api.model.PunishmentExecutable;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PunishmentImpl implements Punishment {

    private final String key;
    private final String reason;
    private final long forgivenessOffset;
    private final List<PunishmentExecutable> executables;

    public PunishmentImpl(String key, ConfigurationSection section) {
        this.key = key;
        this.reason = section.getString("reason");
        this.forgivenessOffset = section.getLong("forgiveness", -1);

        List<PunishmentExecutable> list = Lists.newArrayList();
        ConfigurationSection table = section.getConfigurationSection("table");

        PunishmentExecutable previous = null;
        for (String tableKey : table.getKeys(false)) {
            ConfigurationSection tableSection = table.getConfigurationSection(tableKey);
            if (tableSection == null)
                continue;

            PunishmentExecutable punishmentExecutable = new PunishmentExecutableImpl(previous, tableKey, tableSection);
            list.add(punishmentExecutable);

            previous = punishmentExecutable;
        }

        this.executables = list;
    }

    public PunishmentImpl(String key, String reason, long forgivenessOffset, List<PunishmentExecutable> executables) {
        this.key = key;
        this.reason = reason;
        this.forgivenessOffset = forgivenessOffset;
        this.executables = executables;
    }

    @Override
    public @NotNull String getReason() {
        return reason;
    }

    @Override
    public long getForgivenessOffset() {
        return forgivenessOffset;
    }

    @Override
    public @NotNull List<PunishmentExecutable> getExecutables() {
        return executables;
    }

    @Override
    public @NotNull String getKey() {
        return key;
    }

    @Override
    public void setKey(@NotNull String s) {
        throw new UnsupportedOperationException("Cannot set key of punishment");
    }
}
