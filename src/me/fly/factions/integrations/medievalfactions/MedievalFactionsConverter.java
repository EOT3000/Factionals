package me.fly.factions.integrations.medievalfactions;

import com.dansplugins.factionsystem.MedievalFactions;
import com.dansplugins.factionsystem.claim.MfClaimedChunk;
import com.dansplugins.factionsystem.faction.MfFaction;
import com.dansplugins.factionsystem.faction.MfFactionMember;
import com.dansplugins.factionsystem.service.Services;
import me.fly.factions.Factionals;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.User;
import me.fly.factions.api.registries.Registry;
import me.fly.factions.impl.model.FactionImpl;
import me.fly.factions.impl.model.PlotImpl;
import me.fly.factions.impl.model.UserImpl;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MedievalFactionsConverter {
    static Factionals fac = Factionals.getFactionals();
    static Registry<User, UUID> uReg = fac.getRegistry(User.class, UUID.class);
    static Registry<Faction, String> uFac = fac.getRegistry(Faction.class, String.class);
    static MedievalFactions mf = MedievalFactions.getPlugin(MedievalFactions.class);
    static Services medievalFactions = mf.getServices();

    private static Random random = new Random();

    public static void convertIfContains() {
        boolean containsData = uFac.list().size() > 0;

        if(!containsData) {
            for(MfFaction faction : medievalFactions.getFactionService().getFactions()) {
                for(MfFactionMember player : faction.getMembers()) {
                    OfflinePlayer bukkit = getUser(player);

                    if(uReg.get(bukkit.getUniqueId()) == null) {
                        User user = new UserImpl(bukkit);

                        user.setPower(4500);

                        uReg.set(bukkit.getUniqueId(), user);
                    }
                }

                Faction ourFaction = new FactionImpl(getLeader(faction), faction.getName().replaceAll(" ", "_"));

                uFac.set(ourFaction.getName(), ourFaction);

                Color color = Color.fromRGB(Integer.decode(faction.getFlags().get(mf.flags.getColor())));

                ourFaction.setBorderColor(color);
                ourFaction.setFillColor(color);

                if(faction.getHome() != null) {
                    ourFaction.setHome(faction.getHome().toBukkitLocation());
                }

                for(MfFactionMember member : faction.getMembers()) {
                    uReg.get(getUser(member).getUniqueId()).setFaction(ourFaction);
                }

                for(MfClaimedChunk chunk : medievalFactions.getClaimService().getClaimsByFactionId(faction.getId())) {
                    if(Bukkit.getWorld(chunk.getWorldId()) == null) {
                        continue;
                    }
                    new PlotImpl(chunk.getX(), chunk.getZ(), Bukkit.getWorld(chunk.getWorldId()), ourFaction);
                }
            }
        }
    }

    private static User getLeader(MfFaction faction) {
        List<MfFactionMember> potentialLeaders = new ArrayList<>();

        for(MfFactionMember player : faction.getMembers()) {
            Boolean v = player.getRole().getPermissionValue(mf.factionPermissions.getDisband());

            if((v != null && v) || player.getRole().getName().equalsIgnoreCase("owner")) {
                potentialLeaders.add(player);
            }
        }

        OfflinePlayer h = null;
        long last = 0;

        for(MfFactionMember player : potentialLeaders) {
            OfflinePlayer bukkit = getUser(player);

            if(bukkit.getLastSeen() > last) {
                h = bukkit;
                last = bukkit.getLastSeen();
            }
        }

        if(h != null) {
            return uReg.get(h.getUniqueId());
        }

        return uReg.get(getUser(faction.getMembers().get(0)).getUniqueId());
    }

    private static OfflinePlayer getUser(MfFactionMember player) {
        return medievalFactions.getPlayerService().getPlayerByPlayerId(player.getPlayerId()).toBukkit();
    }
}
