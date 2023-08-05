package me.fly.factions;

import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.PlayerGroup;
import me.fly.factions.api.model.Plot;
import me.fly.factions.api.model.User;
import me.fly.factions.api.model.organizations.Organization;
import me.fly.factions.api.registries.Registry;
import me.fly.factions.api.serialization.Serializer;
import me.fly.factions.impl.commands.FactionCommand;
import me.fly.factions.impl.commands.PlotCommand;
import me.fly.factions.impl.commands.faction.FactionCommands;
import me.fly.factions.impl.configuration.Configuration;
import me.fly.factions.impl.dynmap.Dynmap;
import me.fly.factions.impl.listeners.ChatListener;
import me.fly.factions.impl.listeners.DeathListener;
import me.fly.factions.impl.listeners.JoinLeaveListener;
import me.fly.factions.impl.listeners.PlotListener;
import me.fly.factions.impl.registries.RegistryImpl;
import me.fly.factions.impl.registries.StringRegistryImpl;
import me.fly.factions.impl.serialization.FactionSerializer;
import me.fly.factions.impl.serialization.OrganizationSerializer;
import me.fly.factions.impl.serialization.UserSerializer;
import me.fly.factions.impl.util.Plots;
import me.fly.factions.impl.util.Ticker;
import me.fly.factions.integrations.medievalfactions.MedievalFactionsConverter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.markers.MarkerSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Factionals extends JavaPlugin implements Listener, PlayerGroup {
    private Map<Class<?>, Registry> registries = new HashMap<>();

    private FactionCommand factionCommand;
    private PlotCommand plotCommand;

    private Configuration configuration;

    private static Factionals FACTIONALS;
    private Logger logger = Bukkit.getLogger();

    private MarkerSet markerSet;

    public boolean debug = false;

    public Factionals() {
        FACTIONALS = this;
    }

    public static Factionals getFactionals() {
        return FACTIONALS;
    }


    @Override
    public void onEnable() {
        Plots.init();

        logger.info(ChatColor.DARK_AQUA + "---------------------------------------------");
        logger.info(ChatColor.RED + "Starting Factionals!");
        logger.info(ChatColor.DARK_AQUA + "---------------------------------------------");


        registries.put(Faction.class, new StringRegistryImpl<>(Faction.class));
        registries.put(Organization.class, new StringRegistryImpl<>(Organization.class));
        registries.put(Serializer.class, new RegistryImpl<Serializer, Class>(Serializer.class));
        registries.put(User.class, new RegistryImpl<User, UUID>(User.class));
        registries.put(Plot.class, new RegistryImpl<Plot, Integer>(Plot.class));

        new FactionSerializer(this);
        new UserSerializer(this);
        new OrganizationSerializer(this);


        new JoinLeaveListener();
        new PlotListener();
        new ChatListener();
        new DeathListener();
        //new MenusListener();


        new Dynmap();


        //economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();

        configuration = new Configuration();


        //factionCommand = new FactionCommand(this);
        //plotCommand = new PlotCommand(this);

        new FactionCommands();


        Collection<User> userList = Serializer.loadAll(User.class);

        for(User user : userList) {
            registries.get(User.class).set(user.getUniqueId(), user);
        }

        Collection<Faction> factionList = Serializer.loadAll(Faction.class);

        for(Faction faction : factionList) {
            registries.get(Faction.class).set(faction.getName(), faction);
        }
        
        Serializer.loadAll(Faction.class);

        Collection<Organization> orgList = Serializer.loadAll(Organization.class);

        for(Organization org : orgList) {
            registries.get(Organization.class).set(org.getName(), org);
        }

        //onDisable();

        //System.exit(0);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            logger.info("Faction autosave start");

            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {

                File dir2 = new File(FactionSerializer.dir.getAbsolutePath() + "/previous");

                if (!dir2.exists()) {
                    dir2.mkdir();
                }

                for (File file : FactionSerializer.dir.listFiles()) {
                    try {
                        if (!file.isDirectory()) {
                            com.google.common.io.Files.copy(file, new File(FactionSerializer.dir.getAbsolutePath() + "/previous/" + file.getName()));
                        }
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, file.getName() + " has errored on autosave (facs)");

                        e.printStackTrace();
                    }
                }

                File dir3 = new File(OrganizationSerializer.dir.getAbsolutePath() + "/previous");

                if (!dir3.exists()) {
                    dir3.mkdir();
                }

                for (File file : OrganizationSerializer.dir.listFiles()) {
                    try {
                        if (!file.isDirectory()) {
                            com.google.common.io.Files.copy(file, new File(OrganizationSerializer.dir.getAbsolutePath() + "/previous/" + file.getName()));
                        }
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, file.getName() + " has errored on autosave (orgs)");

                        e.printStackTrace();
                    }
                }

                Serializer.saveAll(registries.get(Faction.class).list(), Faction.class);

                Serializer.saveAll(registries.get(User.class).list(), User.class);

                Serializer.saveAll(registries.get(Organization.class).list(), Organization.class);

                logger.info("Autosaved factions");
            });
        }, 6000, 6000);

        Bukkit.getScheduler().runTaskTimer(this, Ticker::tick, 20, 120);
        Bukkit.getScheduler().runTaskTimer(this, Ticker::tickHour, 20, 72000);

        dortps();



        /*int count = 0;

        for(int v = 0; v < 5; v++) {
            for(int s = 0; s < 6; s++) {
                for(int h = 0; h < 9; h++) {
                    double h2 = h/8.1;
                    double s2 = s/1.0;
                    double v2 = v/4.0;

                    ItemStack stack = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();

                    meta.setColor(hsvToRgb(h2, s2, v2));
                    meta.getPersistentDataContainer().set(MenuType.MENU_NAMESPACE, PersistentDataType.STRING, "colors1");
                    meta.setDisplayName("" + count++);

                    stack.setItemMeta(meta);

                    colors.add(stack);
                }
            }
        }*/

        //TODO: FIX THIS HUJDsinjkfjKHJENUDIWQHG(OI#@HNFuiwh9832rhiuewdnsaio

        MedievalFactionsConverter.convertIfContains();
    }

    private void dortps() {
        System.out.println("reloading rtps");

        locs = new ArrayList<>();

        File rtps = new File("plugins\\Factionals\\rtps.txt");

        try {
            List<String> ss = Files.lines(rtps.toPath()).collect(Collectors.toList());

            for(String s : ss) {
                String[] spl = s.split(",");

                int x = Integer.parseInt(spl[0]);
                int y = Integer.parseInt(spl[1]);
                int z = Integer.parseInt(spl[2]);

                World world = Bukkit.getWorld(spl[3]);

                locs.add(new Location(world,x,y,z));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Faction> menuListable(User user) {
        return new ArrayList<>(getRegistry(Faction.class).list());
    }

    /*private OpenedMenu.Button menuButton(Material material, String name, String button) {
        return new OpenedMenu.Button((x, y) -> x.setMenu(((Registry<MenuType, String>) getRegistry(MenuType.class)).get(button).create(x))) {
            @Override
            public ItemStack getItem() {
                ItemStack stack = new ItemStack(material);
                ItemMeta meta = stack.getItemMeta();

                meta.setDisplayName(name);

                stack.setItemMeta(meta);

                return stack;
            }
        };
    }*/

    @SuppressWarnings("unchecked")
    public <V> Registry<V, ?> getRegistry(Class<V> clazz) {
        return (Registry<V, ?>) registries.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public <V, K> Registry<V, K> getRegistry(Class<V> clazz, Class<K> clazz2) {
        return (Registry<V, K>) registries.get(clazz);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void onDisable() {
        System.out.println(registries.get(Faction.class).list());

        Serializer.saveAll(registries.get(Faction.class).list(), Faction.class);
        Serializer.saveAll(registries.get(User.class).list(), User.class);
    }


    //TODO: move to other plugin
    private List<Location> locs = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("rtpad") && sender.isOp()) {
            attemptTp(args[0], 5);
        } else if(command.getName().equalsIgnoreCase("rtpa")) {
            if(sender.isOp()) {
                locs.add(((Player) sender).getLocation().clone());
            }
        } else if(command.getName().equalsIgnoreCase("rtpc")) {
            if(sender.isOp()) {
                dortps();
            }
        } else if(command.getName().equalsIgnoreCase("fdebug")) {
            sender.sendMessage("-----");

            sender.sendMessage("before: " + debug);

            debug = !debug;

            sender.sendMessage("now: " + debug);

            sender.sendMessage("-----");
        }

        return true;
    }

    public void attemptTp(String s, int attempts) {
        if(attempts <= 0) {
            Bukkit.getPlayer(s).sendMessage(ChatColor.RED + "Failed to find a safe random teleport location in 5 attempts. Please try again");

            return;
        }

        Random random = new Random();

        Location loc = locs.get(random.nextInt(locs.size())).clone().add(random.nextInt(20)-10,0,random.nextInt(20)-10);

        loc = loc.getWorld().getHighestBlockAt(loc).getLocation().clone().add(0,1,0);

        Registry<Plot, Integer> registry = getRegistry(Plot.class, Integer.class);

        if(registry.get(Plots.getLocationId(loc)) == null) {
            Bukkit.getPlayer(s).teleport(loc);
        } else {
            attemptTp(s, attempts-1);
        }
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {

    }

    /*@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        int x = Plot.getLocationId(event.getClickedBlock().getLocation());
        User user = getUserFromPlayer(event.getPlayer());
        Faction faction = user.getFaction();
        Faction plotFaction = plots.get(x);

        if(plotFaction == null) {
            return;
        } else {
            if(plotFaction.isDeleted()) {
                plots.remove(x);
                return;
            }
        }

        String permissionsList = plotFaction.getPlots().get(x).getKey();

        if(permissionsList.contains("u" + user.getUuid().toString()) || permissionsList.contains("f" + faction.getName()) || faction.hasPermission(user, Permission.USE_ALL)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Faction factionFrom = plots.get(Plot.getLocationId(event.getFrom()));
        Faction factionTo = plots.get(Plot.getLocationId(event.getTo()));

        if(factionFrom == factionTo) {
            return;
        }

        if(factionTo == null) {
            event.getPlayer().sendTitle(new Title(ChatColor.DARK_GREEN + "Entering Wilderness!", "", 5, 10, 5));
        } else {
            event.getPlayer().sendTitle(new Title(ChatColor.GREEN + "Entering " + ChatColor.YELLOW + factionTo.name() + ChatColor.GREEN + "!", "", 5, 10, 5));
        }
    }*/

    @Override
    public void broadcast(String s) {
        logger.info(s);

        for(User user : getMembers()) {
            user.sendMessage(s);
        }
    }

    @Override
    public Set<User> getMembers() {
        return new HashSet<>(getRegistry(User.class, UUID.class).list());
    }
}
