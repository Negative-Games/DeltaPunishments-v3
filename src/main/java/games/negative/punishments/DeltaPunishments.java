package games.negative.punishments;

import games.negative.framework.BasePlugin;
import games.negative.punishments.api.PunishAPI;
import games.negative.punishments.core.provider.PunishAPIProvider;

public final class DeltaPunishments extends BasePlugin {

    private PunishAPI api;

    @Override
    public void onEnable() {
        super.onEnable();

        loadFiles(this, "punishments.yml");

        this.api = new PunishAPIProvider(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
