package fly.factions.impl.serialization;

import fly.factions.Factionals;
import fly.factions.api.model.*;
import fly.factions.api.model.organizations.InternationalOrganization;
import fly.factions.api.model.organizations.Organization;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.api.permissions.Permissibles;
import fly.factions.api.permissions.PlotPermission;
import fly.factions.api.registries.Registry;
import fly.factions.api.serialization.Serializer;
import fly.factions.impl.model.*;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class OrganizationSerializer extends Serializer<Organization> {
    public static final File dir = new File("plugins\\Factionals\\organizations");

    public OrganizationSerializer(Factionals factionals) {
        super(Organization.class, factionals);
    }

    @Override
    public File dir() {
        return dir;
    }

    @Override
    public Organization load(File file) {
        String type = "pre-init";
        String exact = "";

        try {

            Registry<User, UUID> r = factionals.getRegistry(User.class, UUID.class);

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            if (!configuration.getBoolean("deleted")) {
                if(configuration.getString("type").equalsIgnoreCase("international")) {
                    InternationalOrganization io = new InternationalOrganizationImpl(configuration.getString("name"), r.get(UUID.fromString(configuration.getString("leader"))), configuration.getLong("creationTime"));

                    type = "plots";

                    ConfigurationSection plots = configuration.getConfigurationSection("plots");

                    for (String string : plots.getKeys(false)) {
                        exact = string;

                        ConfigurationSection plot = plots.getConfigurationSection(string);

                        if (Plots.getWorld(plot.getInt("w")) == null) {
                            continue;
                        }

                        new PlotImpl(plot.getInt("x"), plot.getInt("z"), Plots.getWorld(plot.getInt("w")), io);
                    }

                    ConfigurationSection orgmembers = configuration.getConfigurationSection("orgmembers");

                    for(String string : orgmembers.getKeys(false)) {
                        io.addMemberOrganization((FactionComponent) Permissibles.get(string));
                    }

                    Factionals.getFactionals().getLogger().info("Loaded organization " + io.getName());

                    return io;
                }
            }
        } catch (Exception e) {
            Factionals.getFactionals().getLogger().severe("Error in organization loading " + file.getName() + ", " + type + "; " + exact);

            e.printStackTrace();
        }

        return null;
    }

    private PlotOwner getPlotOwner(String owner) {
        List<Permissible> permissibles = Permissibles.get(owner);

        if(owner.isEmpty()) {
            return null;
        }

        for(Permissible permissible : permissibles) {
            if(permissible instanceof PlotOwner) {
                return (PlotOwner) permissible;
            }
        }

        return null;
    }

    @Override
    public Pair<YamlConfiguration, File> save(Organization o) {
        InternationalOrganization io = (InternationalOrganization) o;

        File file = new File("plugins\\Factionals\\organizations\\" + io.getCreationTime() + "-" + io.getName());

        YamlConfiguration configuration = new YamlConfiguration();

        Map<Integer, Map<String, Object>> plots = new HashMap<>();
        //List<String> members = new ArrayList<>();
        List<String> orgmembers = new ArrayList<>();

        for(Plot plot : io.getPlots()) {
            Map<String, Object> factionPlot = new HashMap<>();

            factionPlot.put("x", Plots.getX(plot.getLocationId()));
            factionPlot.put("z", Plots.getZ(plot.getLocationId()));
            factionPlot.put("w", Plots.getW(plot.getLocationId()));

            plots.put(plot.getLocationId(), factionPlot);
        }

        for(FactionComponent member : io.getMemberOrganizations()) {
            orgmembers.add(member.getId());
        }

        configuration.set("plots", plots);
        configuration.set("orgmembers", orgmembers);

        configuration.set("creationTime", io.getCreationTime());
        configuration.set("leader", io.getLeader().getUniqueId().toString());
        configuration.set("type", "international");
        configuration.set("name", io.getName());

        return new Pair<>(configuration, file);
    }
}
