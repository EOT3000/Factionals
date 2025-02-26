package me.fly.factions.api.model;

import me.fly.factions.api.claiming.ClaimType;

import java.util.Collection;
import java.util.UUID;

public interface User extends PlotOwner {
    UUID getUniqueId();

    void sendMessage(String s);

    Faction getFaction();

    /**
     * Sets the user's faction. Also removes itself from the old faction's member list, and adds itself to the new faction member list
     *
     * @param faction the faction that the user will be joining
     */

    void setFaction(Faction faction);

    Collection<Faction> getInvites();
    void addInvite(Faction faction);
    void removeInvite(Faction faction);

    boolean isAdminMode();

    ClaimType getAutoClaiming();

    void setAutoClaiming(ClaimType autoClaiming);

    int getPower();

    void setPower(int x);

    void setAdminMode(boolean adminMode);

    void updateName();

    void setFactionChat(boolean b);

    boolean getFactionChat();
}
