package games.negative.punishments.api.model;

import lombok.Getter;

/**
 * Represents a type of punishment
 */
@Getter
public enum PunishmentType {

    BAN("ban", "bans", "tempban", "ban"),
    MUTE("mute", "mutes", "tempmute", "mute"),
    KICK("kick", "kicks", null, "kick"),
    WARN("warn", "warnings", null, "warn"),
    IPBAN("ipban", null, "tempipban", "ipban"),
    IPMUTE("ipmute", null, "tempipmute", "ipmute"),
    IPWARN("ipwarn", null, null, "ipwarn"),
    ;

    private final String name;
    private final String table;
    private final String tempKeyword;
    private final String permKeyword;

    PunishmentType(String name, String table, String tempKeyword, String permKeyword) {
        this.name = name;
        this.table = table;
        this.tempKeyword = tempKeyword;
        this.permKeyword = permKeyword;
    }

    public String getKeyword(boolean perm) {
        return perm ? permKeyword : tempKeyword;
    }

}
