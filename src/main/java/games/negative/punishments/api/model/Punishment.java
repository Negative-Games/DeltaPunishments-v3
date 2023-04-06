package games.negative.punishments.api.model;

import games.negative.framework.key.Keyd;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This is a model class to represent a Punishment from punishments.yml
 */
public interface Punishment extends Keyd<String> {

    @NotNull
    String getReason();

    long getForgivenessOffset();

    @NotNull
    List<PunishmentExecutable> getExecutables();

}
