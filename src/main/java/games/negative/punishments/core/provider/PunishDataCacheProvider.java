package games.negative.punishments.core.provider;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import games.negative.punishments.api.PunishDataCache;
import games.negative.punishments.api.model.PunishmentRecord;
import games.negative.punishments.api.model.PunishmentType;
import games.negative.punishments.core.structure.PunishmentRecordImpl;
import games.negative.punishments.core.util.SafeSQL;
import litebans.api.Database;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class PunishDataCacheProvider implements PunishDataCache {

    private final Multimap<UUID, PunishmentRecord> cache;
    private final BukkitTask task;

    public PunishDataCacheProvider(JavaPlugin plugin) {
        this.cache = ArrayListMultimap.create();
        this.task = new CacheTask().runTaskTimerAsynchronously(plugin, 0L, 20 * 10);
    }

    @Override
    public void cache() {
        cache.clear();

        Collection<PunishmentRecord> all = Lists.newArrayList();
        all.addAll(loadPunishments(PunishmentType.BAN));
        all.addAll(loadPunishments(PunishmentType.MUTE));
        all.addAll(loadPunishments(PunishmentType.KICK));
        all.addAll(loadPunishments(PunishmentType.WARN));

        for (PunishmentRecord punishment : all) {
            cache.put(punishment.getUniqueID(), punishment);
        }

        all.clear();
    }

    @Override
    public void clearCache() {
        cache.clear();
    }

    @Override
    public BukkitTask getCacheTask() {
        return task;
    }

    @Override
    public @NotNull Collection<PunishmentRecord> retrieveRecords(@NotNull UUID uuid, @Nullable PunishmentType type) {
        return (type == null ? cache.get(uuid) : cache.get(uuid).stream().filter(entry -> entry.getType().equals(type)).collect(Collectors.toList()));
    }


    // todo make this prettier
    private Collection<PunishmentRecord> loadPunishments(@NotNull PunishmentType type) {
        Preconditions.checkNotNull(type, "type cannot be null");
        Preconditions.checkNotNull(type.getTable(), "type table cannot be null");

        Collection<PunishmentRecord> collection = Lists.newArrayList();
        try (PreparedStatement statement = Database.get().prepareStatement("SELECT * FROM {" + type.getTable() + "}")){
            try (ResultSet result = statement.executeQuery()){
                while(result.next()) {
                    long id = result.getLong("id");
                    String reason = result.getString("reason");
                    String bannedByName = result.getString("banned_by_name");
                    String uuid = result.getString("uuid");
                    long time = result.getLong("time");
                    long until = result.getLong("until");
                    boolean active = result.getBoolean("active");

                    String removed = SafeSQL.getString(result, "removed_by_uuid");
                    String removeReason = SafeSQL.getString(result, "removed_by_reason");

                    UUID parsedUUID;
                    try {
                        parsedUUID = UUID.fromString(uuid);
                    } catch (Exception ignored) {
                        continue;
                    }

                    PunishmentRecord record = new PunishmentRecordImpl(id, parsedUUID, reason, type, bannedByName, removed, removeReason, time, until, active);
                    collection.add(record);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collection;
    }


    private class CacheTask extends BukkitRunnable {

        @Override
        public void run() {
            cache();
        }
    }
}
