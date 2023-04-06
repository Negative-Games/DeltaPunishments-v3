package games.negative.punishments.api.model;

import com.google.common.collect.Lists;
import games.negative.framework.key.Keyd;
import games.negative.framework.util.TimeUtil;
import games.negative.framework.util.Utils;
import games.negative.punishments.core.util.TextBuilder;
import games.negative.punishments.core.util.TextUtil;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents an entry on the punishment executable table which
 * will hold data on the following:
 * - `type` of punishment (ban, mute, etc)
 * - `duration` of punishment
 * - `commands` of the punishment (commands to be executed after the punishment is successfully executed)
 */
public interface PunishmentExecutable extends Keyd<String> {

    @Nullable
    PunishmentExecutable getParent();

    @NotNull
    PunishmentType getType();

    @NotNull
    String getDuration();

    @Nullable
    String getPermission();

    /**
     * Gets the "true" permission node of the executable.
     * What this will do is basically looks through all the parents
     * and checks their permissions, if they have one, it will
     * return that permission node.
     * @return the permission node if found, null otherwise.
     */
    @Nullable
    default String getTruePermission() {
        String perm = getPermission();
        if (perm != null)
            return perm;

        PunishmentExecutable parent = getParent();
        if (parent == null)
            return null;

        while (parent != null) {
            perm = parent.getPermission();
            if (perm != null)
                return perm;

            parent = parent.getParent();
        }
        return null;
    }

    default long getDurationMills() {
        String duration = getDuration();
        // Checking the duration for the "permanent" keyword.
        if (TextUtil.multiEqualsIgnoreCase(duration, "permanent", "perm", "forever"))
            return -1;

        return TimeUtil.longFromString(duration);
    }

    List<String> getCommands();

    /**
     * Executes the commands of this punishment executable.
     * @param target the target of the punishment
     */
    default void executeCommands(@NotNull OfflinePlayer target) {
        List<String> commands = Lists.newArrayList(getCommands());
        for (String command : commands) {
            TextBuilder builder = new TextBuilder(command).replace("%target%", target.getName());

            Utils.executeConsoleCommand(builder.build());
        }
    }
}
