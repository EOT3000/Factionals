package me.fly.factions.api.commands;

import com.google.common.collect.Lists;
import me.fly.factions.Factionals;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Plot;
import me.fly.factions.api.model.PlotType;
import me.fly.factions.api.model.User;
import me.fly.factions.api.model.organizations.InternationalOrganization;
import me.fly.factions.api.model.organizations.Organization;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.api.permissions.Permissibles;
import me.fly.factions.api.permissions.PlotPermission;
import me.fly.factions.api.registries.Registry;
import me.fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

public abstract class CommandDivision implements CommandExecutor, TabExecutor {
    protected static final Factionals API = Factionals.getFactionals();
    protected static final Registry<User, UUID> USERS = API.getRegistry(User.class, UUID.class);
    protected static final Registry<Plot, Integer> PLOTS = API.getRegistry(Plot.class, Integer.class);
    protected static final Registry<Faction, String> FACTIONS = API.getRegistry(Faction.class, String.class);
    protected static final Registry<Organization, String> ORGANIZATIONS = API.getRegistry(Organization.class, String.class);

    protected static final Pattern pattern = Pattern.compile("^((?!([a-z]|[A-Z]|[0-9]|_)).)*$");

    private Map<String, List<CommandDivision>> subCommands = new HashMap<>();
    private List<Pair<String, String>> helpEntries = new ArrayList<>();

    protected CommandDivision() {

    }

    protected CommandDivision(String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    protected CommandDivision(String command, String s2) {
        Bukkit.getPluginCommand(command).setExecutor(this);
        Bukkit.getPluginCommand(s2).setExecutor(this);
    }

    protected CommandDivision(String command, String s2, String s3) {
        Bukkit.getPluginCommand(command).setExecutor(this);
        Bukkit.getPluginCommand(s2).setExecutor(this);
        Bukkit.getPluginCommand(s3).setExecutor(this);
    }

    protected CommandDivision(String command, String s2, String s3, String s4) {
        Bukkit.getPluginCommand(command).setExecutor(this);
        Bukkit.getPluginCommand(s2).setExecutor(this);
        Bukkit.getPluginCommand(s3).setExecutor(this);
        Bukkit.getPluginCommand(s4).setExecutor(this);
    }

    protected CommandDivision(String command, String s2, String s3, String s4, String... a) {
        Bukkit.getPluginCommand(command).setExecutor(this);
        Bukkit.getPluginCommand(s2).setExecutor(this);
        Bukkit.getPluginCommand(s3).setExecutor(this);
        Bukkit.getPluginCommand(s4).setExecutor(this);

        for(String s : a) {
            Bukkit.getPluginCommand(s).setExecutor(this);
        }
    }

    protected final void addSubCommand(String command, CommandDivision division) {
        if(!subCommands.containsKey(command)) {
            subCommands.put(command, new ArrayList<>());
        }

        subCommands.get(command).add(division);
    }

    protected final void addHelpEntry(String syntax, String description) {
        helpEntries.add(new Pair<>(syntax, description));
    }

    protected final int constrain(int min, int max, int value) {
        return Math.min(max, Math.max(min,value));
    }

    protected final double constrain(double min, double max, double value) {
        return Math.min(max, Math.max(min,value));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        CommandDivision division = getNext(strings.length != 0 ? strings[0] : "");

        if(division == null) {
            help(commandSender, 0);

            return false;
        }

        if(division == this) {
            Class clazz = division.getClass();

            Method[] methods = clazz.getDeclaredMethods();

            for(Method method : methods) {
                if(method.getName().equalsIgnoreCase("run")) {
                    try {
                        for(Pair<CommandRequirement, Object> pair : division.getUserRequirements()) {
                            if(!pair.getKey().has(commandSender, parse(pair.getValue(), strings))) {
                                commandSender.sendMessage(pair.getKey().format(commandSender));

                                return false;
                            }
                        }

                        int count = 0;

                        for(ArgumentType type : division.getRequiredTypes()) {
                            if(!type.check(strings[count])) {
                                commandSender.sendMessage(type.format(strings[count]));
                                
                                return false;
                            }

                            count++;
                        }

                        if(division.getRequiredTypes().length >= 1 && division.getRequiredTypes()[0].equals(ArgumentType.STRINGS)) {
                            return (boolean) method.invoke(division, commandSender, (Object) strings);
                        } else {
                            return (boolean) method.invoke(division, pushIntoStart(strings, commandSender));
                        }
                    } catch (ArrayIndexOutOfBoundsException | InvocationTargetException e) {
                        if(e instanceof InvocationTargetException && !(e.getCause() instanceof ArrayIndexOutOfBoundsException)) {
                            e.printStackTrace();

                            int num = new Random().nextInt(2000);

                            commandSender.sendMessage(ChatColor.DARK_RED + "Fatal error at line " + num);
                            commandSender.sendMessage(ChatColor.DARK_RED + e.getClass().getName() + "@" + e.hashCode());
                            commandSender.sendMessage(ChatColor.DARK_RED + "Please contact the head of servers or administrators, Fly");

                            System.out.println("crashed server at time " + num);

                            return false;
                        }

                        commandSender.sendMessage(ChatColor.RED + "ERROR: too many or not enough arguments");

                        return false;
                    } catch(Exception e) {
                        e.printStackTrace();

                        int num = new Random().nextInt(2000);

                        commandSender.sendMessage(ChatColor.DARK_RED + "Fatal error at line " + num);
                        commandSender.sendMessage(ChatColor.DARK_RED + e.getClass().getName() + "@" + e.hashCode());
                        commandSender.sendMessage(ChatColor.DARK_RED + "Please contact the head of servers or administrators, Fly");

                        System.out.println("crashed server at time " + num);

                        return false;
                    }
                }
            }

            return false;
        }

        division.onCommand(commandSender, command, s, clean(strings));

        return false;
    }

    /*public static void main(String[] args) {
        CommandDivision division = new FactionCommands();

        System.out.println(division.onTabComplete(null, null, null, new String[] {"c"}));
    }*/

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        CommandDivision division = this;
        CommandDivision divisionOld;

        for(int i = 0; i < strings.length; i++) {
            String string = strings[i];

            divisionOld = division;

            division = division.getNext(string.isEmpty() ? "&&&&" : string);

            if(division == null) {
                if(i != strings.length-1) {
                    return new ArrayList<>();
                } else {
                    List<String> list = new ArrayList<>();

                    for (String possible : divisionOld.subCommands.keySet()) {
                        if (possible.toLowerCase().startsWith(string.toLowerCase())) {
                            list.add(possible);
                        }
                    }

                    return list;
                }
            } else if(division.equals(divisionOld)) {
                List<String> list = new ArrayList<>();

                int a = strings.length-i-1;

                if(divisionOld.getRequiredTypes().length > a) {
                    for(String p : divisionOld.getRequiredTypes()[a].list()) {
                        if(p.toLowerCase().startsWith(strings[strings.length-1].toLowerCase())) {
                            list.add(p);
                        }
                    }
                }

                return list;
            }
        }

        return new ArrayList<>();
    }

    private Object parse(Object obj, String[] args) {
        if(obj instanceof Integer) {
            return args[(Integer) obj];
        }

        return obj;
    }
    
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[0];
    }
    
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[0];
    }

    //TODO: deal with manual array copy

    private static String[] clean(String[] strings) {
        String[] ret = new String[strings.length-1];

        for(int i = 1; i < strings.length; i++) {
            ret[i-1] = strings[i];
        }

        return ret;
    }

    private static Object[] pushIntoStart(Object[] objects, Object insert) {
        Object[] ret = new Object[objects.length+1];

        for(int i = 0; i < objects.length; i++) {
            ret[i+1] = objects[i];
        }

        ret[0] = insert;

        return ret;
    }

    // If something, the sub command
    // If nothing but it terminates here, itself
    // If nothing and it doesn't terminate here, null
     public CommandDivision getNext(String string) {
         if(subCommands.containsKey(string)) {
             return subCommands.get(string).get(0);
         }
        
        if(string.isEmpty()) {
            return null;
        }
        
        if(subCommands.containsKey("*")) {
            return this;
        }

        return null;
    }

    public void help(CommandSender sender, int page) {
        sender.sendMessage(ChatColor.GOLD + "Help");

        for(Pair<String, String> command : helpEntries) {
            sender.sendMessage(ChatColor.DARK_AQUA + command.getKey() + ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + command.getValue());
        }
    }

    protected static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    protected static boolean nameGood(String name) {
        if(pattern.matcher(name).matches()) {
            return false;
        }

        return name.length() <= 24 && name.length() >= 3;
    }

    public enum ArgumentType {
        INT {
            @Override
            public boolean check(String string) {
                try {
                    Integer.parseInt(string);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: " + ChatColor.YELLOW + string + ChatColor.RED + " needs to be an integer";
            }

            @Override
            public List<String> list() {
                return new ArrayList<>();
            }
        },
        LONG {
            @Override
            public boolean check(String string) {
                try {
                    Long.parseLong(string);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: " + ChatColor.YELLOW + string + ChatColor.RED + " needs to be a long";
            }

            @Override
            public List<String> list() {
                return new ArrayList<>();
            }
        },
        DOUBLE {
            @Override
            public boolean check(String string) {
                try {
                    Double.parseDouble(string);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: " + ChatColor.YELLOW + string + ChatColor.RED + " needs to be a double";
            }

            @Override
            public List<String> list() {
                return new ArrayList<>();
            }
        },

        CHOICE {
            @Override
            public boolean check(String string) {
                return string.toLowerCase().replace("on", "false").replace("off", "false").replace("true", "false").equalsIgnoreCase("false");
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: " + ChatColor.YELLOW + string + ChatColor.RED + " needs to be a 'on', 'off', 'true' or 'false'";
            }

            @Override
            public List<String> list() {
                return Lists.asList("true", new String[] {"false", "on", "off"});
            }
        },

        STRING {
            @Override
            public boolean check(String string) {
                return true;
            }

            @Override
            public String format(String string) {
                return "";
            }

            @Override
            public List<String> list() {
                return new ArrayList<>();
            }
        },

        STRINGS {
            @Override
            public boolean check(String string) {
                return true;
            }

            @Override
            public String format(String string) {
                return "";
            }

            @Override
            public List<String> list() {
                return new ArrayList<>();
            }
        },

        FACTION {
            @Override
            public boolean check(String string) {
                return FACTIONS.get(string) != null;
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the faction " + ChatColor.YELLOW + string + ChatColor.RED + " does not exist";
            }

            @Override
            public List<String> list() {
                List<String> list = new ArrayList<>();

                for(Faction faction : FACTIONS.list()) {
                    list.add(faction.getName());
                }

                return list;
            }
        },
        NOT_FACTION {
            @Override
            public boolean check(String string) {
                return !FACTION.check(string);
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the faction " + ChatColor.YELLOW + string + ChatColor.RED + " already exists";
            }

            @Override
            public List<String> list() {
                return new ArrayList<>();
            }
        },
        USER {
            @Override
            public boolean check(String string) {
                return Bukkit.getOfflinePlayer(string) != null;
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the user " + ChatColor.YELLOW + string + ChatColor.RED + " is not online";
            }

            @Override
            public List<String> list() {
                List<String> list = new ArrayList<>();

                for(Player player : Bukkit.getOnlinePlayers()) {
                    list.add(player.getName());
                }

                return list;
            }
        },
        PERMISSIBLE {
            @Override
            public boolean check(String string) {
                return Permissibles.get(string).size() > 0;
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the string " + ChatColor.YELLOW + string + ChatColor.RED + " does not represent a permissible entity";
            }

            @Override
            public List<String> list() {
                List<String> list = new ArrayList<>();

                list.addAll(USER.list());
                list.addAll(FACTION.list());

                return list;
            }
        },
        @SuppressWarnings("all")
        FACTION_PERMISSION {
            @Override
            public boolean check(String string) {
                try {
                    return FactionPermission.valueOf(string.toUpperCase()) != null;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the permission " + ChatColor.YELLOW + string + ChatColor.RED + " does not exist";
            }

            @Override
            public List<String> list() {
                List<String> list = new ArrayList<>();

                for(FactionPermission permission : FactionPermission.values()) {
                    list.add(permission.name());
                }

                return list;
            }
        },
        @SuppressWarnings("all")
        PLOT_PERMISSION {
            @Override
            public boolean check(String string) {
                try {
                    return PlotPermission.valueOf(string.toUpperCase()) != null;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the permission " + ChatColor.YELLOW + string + ChatColor.RED + " does not exist";
            }

            @Override
            public List<String> list() {
                List<String> list = new ArrayList<>();

                for(PlotPermission permission : PlotPermission.values()) {
                    list.add(permission.name());
                }

                return list;
            }
        },
        @SuppressWarnings("all")
        PLOT_TYPE {
            @Override
            public boolean check(String string) {
                try {
                    return PlotType.valueOf(string.toUpperCase()) != null;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the plot type " + ChatColor.YELLOW + string + ChatColor.RED + " does not exist";
            }

            @Override
            public List<String> list() {
                List<String> list = new ArrayList<>();

                for(PlotType permission : PlotType.values()) {
                    list.add(permission.name());
                }

                return list;
            }
        },

        ORGANIZATION {
            @Override
            public boolean check(String string) {
                return ORGANIZATIONS.get(string) != null;
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the organization " + ChatColor.YELLOW + string + ChatColor.RED + " does not exist";
            }

            @Override
            public List<String> list() {
                List<String> list = new ArrayList<>();

                for(Organization organization : ORGANIZATIONS.list()) {
                    list.add(organization.getName());
                }

                return list;
            }
        },

        INTERNATIONAL_ORGANIZATION {
            @Override
            public boolean check(String string) {
                return ORGANIZATIONS.get(string) instanceof InternationalOrganization;
            }

            @Override
            public String format(String string) {
                return ChatColor.RED + "ERROR: the international organization " + ChatColor.YELLOW + string + ChatColor.RED + " does not exist";
            }

            @Override
            public List<String> list() {
                List<String> list = new ArrayList<>();

                for(Organization organization : ORGANIZATIONS.list()) {
                    if(organization instanceof InternationalOrganization) {
                        list.add(organization.getName());
                    }
                }

                return list;
            }
        };

        public static boolean checkAll(CommandSender sender, String[] strings, ArgumentType... argumentTypes) {
            int count = 0;

            for(String string : strings) {
                if(!argumentTypes[count].check(string)) {
                    sender.sendMessage(argumentTypes[count].format(string));

                    return false;
                }

                count++;
            }

            return true;
        }

        public abstract boolean check(String string);

        public abstract String format(String string);

        public abstract List<String> list();
    }
}
