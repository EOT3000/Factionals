package fly.factions.impl.model;

import fly.factions.api.model.Faction;
import fly.factions.api.model.User;
import fly.factions.api.permissions.Permissibles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class UserImpl implements User {
    private final UUID uuid;
    private String name;
    private Faction faction;

    private int power;

    private boolean adminMode = false;

    private boolean autoClaiming;
    //private OpenedMenu menu;

    private final Set<Faction> invites = new HashSet<>();

    public UserImpl(UUID uuid) {
        this(uuid, Bukkit.getOfflinePlayer(uuid).getName());
    }

    public UserImpl(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        Permissibles.add(name, this);
        Permissibles.add(uuid.toString(), this);
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public void sendMessage(String s) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

        if(player.isOnline()) {
            ((Player) player).sendMessage(s);
        }
    }

    @Override
    public double getBalance() {
        return factionals.getEconomy().getBalance(getPlayer());
    }

    @Override
    public void setBalance(double x) {
        double take = -(x-factionals.getEconomy().getBalance(getPlayer()));

        if(take > 0) {
            factionals.getEconomy().withdrawPlayer(getPlayer(), take);
        }
        if(take < 0) {
            factionals.getEconomy().depositPlayer(getPlayer(), take);
        }
    }

    @Override
    public void addToBalance(double x) {
        factionals.getEconomy().depositPlayer(getPlayer(), x);
    }

    @Override
    public void takeFromBalance(double x) {
        factionals.getEconomy().withdrawPlayer(getPlayer(), x);
    }

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));

        item.setItemMeta(meta);

        return item;
    }

    /*@Override
    public OpenedMenu getMenu() {
        return menu;
    }

    @Override
    public void setMenu(OpenedMenu menu) {
        this.menu = menu;

        if(menu != null) {
            Inventory inventory = Bukkit.createInventory(getPlayer(), menu.inventorySize(), menu.title());

            for (int x = 0; x <= menu.size(); x++) {
                ItemStack item = menu.getButton(x).getItem();

                if(item != null) {
                    inventory.setItem(x, item);
                }
            }

            Bukkit.getScheduler().runTaskLater(Factionals.getFactionals(), () -> {
                getPlayer().openInventory(inventory);
                this.menu = menu;
            }, 1);
        }
    }*/

    @Override
    public Faction getFaction() {
        return faction;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setFaction(Faction faction) {
        if(this.faction != null && this.faction != faction) {
            this.faction.removeMember(this);
        }

        if(faction == null) {
            this.faction = null;

            return;
        }

        this.faction = faction;

        faction.addMember(this);
    }

    @Override
    public Collection<Faction> getInvites() {
        return new ArrayList<>(invites);
    }

    @Override
    public void addInvite(Faction faction) {
        invites.add(faction);
    }

    @Override
    public void removeInvite(Faction faction) {
        invites.remove(faction);
    }

    /*@Override
    public int claimMode() {
        return claimMode;
    }*/

    @Override
    public boolean isAutoClaiming() {
        return autoClaiming;
    }

    @Override
    public void setAutoClaiming(boolean autoClaiming) {
        this.autoClaiming = autoClaiming;
    }

    @Override
    public boolean userHasPlotPermissions(User user, boolean owner, boolean pub) {
        return user.equals(this);
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isAdminMode() {
        return adminMode;
    }

    @Override
    public int getPower() {
        return Math.min(faction != null ? faction.getPowerPerPlayer() : 6000, power);
    }

    @Override
    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public void setAdminMode(boolean adminMode) {
        this.adminMode = adminMode;
    }

    @Override
    public String getFormattedName() {
        return "Player " + name;
    }

    @Override
    public void updateName() {
        name = Bukkit.getOfflinePlayer(uuid).getName();

        Permissibles.remove(this);

        Permissibles.add(name, this);
        Permissibles.add(uuid.toString(), this);
    }
}
