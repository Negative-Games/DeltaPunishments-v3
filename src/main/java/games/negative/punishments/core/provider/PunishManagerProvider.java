package games.negative.punishments.core.provider;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import games.negative.punishments.api.PunishDataCache;
import games.negative.punishments.api.PunishManager;
import games.negative.punishments.api.model.Punishment;
import games.negative.punishments.api.model.PunishmentExecutable;
import games.negative.punishments.api.model.PunishmentRecord;
import games.negative.punishments.api.model.PunishmentType;
import games.negative.punishments.core.structure.PunishmentImpl;
import games.negative.punishments.core.util.UtilArray;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PunishManagerProvider implements PunishManager {

    private final JavaPlugin plugin;
    private final Map<String, Punishment> punishments;
    private final PunishDataCache cache;

    public PunishManagerProvider(JavaPlugin plugin) {
        this.plugin = plugin;
        this.punishments = Maps.newHashMap();
        this.cache = new PunishDataCacheProvider(plugin);
    }

    @Override
    public @Nullable Punishment findPunishment(@NotNull String key) {
        return punishments.getOrDefault(key, null);
    }

    @Override
    public Map<String, Punishment> getPunishments() {
        return punishments;
    }

    @Override
    public void reload() {
        punishments.clear();

        File file = new File(plugin.getDataFolder(), "punishments.yml");
        Preconditions.checkArgument(file.exists(), "Could not find 'punishments.yml'");

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section == null)
                continue;

            Punishment punishment = new PunishmentImpl(key, section);
            punishments.put(key, punishment);
        }
    }

    @Override
    public @NotNull PunishDataCache getCache() {
        return cache;
    }

    @Override
    public void executePunishment(CommandSender staff, OfflinePlayer offender, Punishment punishment, boolean silent, boolean skipToFinal) {
        if (staff instanceof Player)
            ((Player) staff).closeInventory();

        UUID uuid = offender.getUniqueId();
        Collection<PunishmentRecord> punishments = cache.retrieveRecords(uuid, null);
        int amountOfPunishments = (int) punishments.stream().filter(entry -> entry.getReason().equalsIgnoreCase(punishment.getReason()))
                .filter(entry -> {
                    long date = entry.getTime();
                    long forgivenessOffset = punishment.getForgivenessOffset();
                    if (forgivenessOffset == -1)
                        return true;

                    // TODO: Add configuration option for removing un-punished
                    // punishments from the calculation

                    long forgivenessDate = (date + forgivenessOffset);
                    return System.currentTimeMillis() < forgivenessDate;
                }).count();

        List<PunishmentExecutable> executables = punishment.getExecutables();

        PunishmentExecutable executable = UtilArray.safeGet(executables, amountOfPunishments, true);
        if (skipToFinal) // Will skip to final offense if this is true
            executable = executables.get(executables.size() - 1);

        String permission = executable.getTruePermission();
        if (permission != null && !staff.hasPermission(permission)) {
            // TODO: Send message
            return;
        }

        PunishmentType type = executable.getType();
        long duration = executable.getDurationMills();

        final String offenderName = offender.getName();
        boolean perm = (duration == -1);
        String cmd = (type.getKeyword(perm) + " " + offenderName + (perm ? "" : " " + duration) + " " + punishment.getReason() + (silent ? " -s" : ""));

        if (staff instanceof Player)
            ((Player) staff).performCommand(cmd);
        else
            plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);

        executable.executeCommands(offender);
    }
}
