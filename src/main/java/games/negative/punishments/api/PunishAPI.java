package games.negative.punishments.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is the entry point to the rest of the backend of the plugin.
 */
public abstract class PunishAPI {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static PunishAPI instance;

    public abstract PunishManager getPunishManager();
}
