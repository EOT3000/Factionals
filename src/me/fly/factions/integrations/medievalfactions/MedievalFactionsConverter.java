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

        if(!containsData && Bukkit.getPluginManager().isPluginEnabled("MedievalFactions")) {
            for(MfFaction faction : medievalFactions.getFactionService().getFactions()) {
                for(MfFactionMember player : faction.getMembers()) {
                    OfflinePlayer bukkit = getUser(player);

                    if(uReg.get(bukkit.getUniqueId()) == null) {
                        uReg.set(bukkit.getUniqueId(), new UserImpl(bukkit));
                    }
                }

                Faction ourFaction = new FactionImpl(getLeader(faction), faction.getName().replaceAll(" ", "_"));

                uFac.set(ourFaction.getName(), ourFaction);

                Color color = Color.fromRGB(Integer.decode(faction.getFlags().get(mf.flags.getColor())));

                System.out.println(color.getRed());
                System.out.println(color.getGreen());
                System.out.println(color.getBlue());
                System.out.println(ourFaction.getName());
                System.out.println();

                ourFaction.setBorderColor(color);
                ourFaction.setFillColor(color);

                for(MfFactionMember member : faction.getMembers()) {
                    uReg.get(getUser(member).getUniqueId()).setFaction(ourFaction);
                }

                int count = 0;

                System.out.println("size");
                System.out.println(medievalFactions.getClaimService().getClaimsByFactionId(faction.getId()).size());

                for(MfClaimedChunk chunk : medievalFactions.getClaimService().getClaimsByFactionId(faction.getId())) {
                    if(count++ < 50) {
                        System.out.println(chunk.getX());
                        System.out.println(chunk.getZ());
                        System.out.println(chunk.getWorldId());
                        System.out.println(Bukkit.getWorld(chunk.getWorldId()));
                        System.out.println();
                    }

                    if(Bukkit.getWorld(chunk.getWorldId()) == null) {
                        continue;
                    }
                    new PlotImpl(chunk.getX(), chunk.getZ(), Bukkit.getWorld(chunk.getWorldId()), ourFaction);
                }

                System.out.println();
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
