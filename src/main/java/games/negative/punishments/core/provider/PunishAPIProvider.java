package games.negative.punishments.core.provider;

import games.negative.punishments.api.PunishAPI;
import games.negative.punishments.api.PunishManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PunishAPIProvider extends PunishAPI {

    private final PunishManager punishManager;
    public PunishAPIProvider(JavaPlugin plugin) {
        setInstance(this);

        this.punishManager = new PunishManagerProvider(plugin);
    }

    @Override
    public PunishManager getPunishManager() {
        return punishManager;
    }
}
