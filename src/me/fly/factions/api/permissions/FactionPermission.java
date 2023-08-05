package me.fly.factions.api.permissions;


public enum FactionPermission {
    //Every permission, including faction disbanding
    OWNER,

    //Create ranks and regions. Assign perms to ranks, and land to regions. Set the faction home
    INTERNAL_MANAGEMENT,

    //Claim/unclaim land
    TERRITORY,

    //Declare allies, enemies, etc
    RELATIONS,

    //Invite players
    USER_ADD,

    //Kick players
    USER_KICK,
}
