package me.fly.factions.api.permissions;

import me.fly.factions.integrations.slimefun.SlimefunIntegration;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.*;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;

public enum PlotPermission {
    //Block placing and breaking
    BUILD {
    },

    //Doors and trapdoors
    DOOR {
        @Override
        public boolean required(Block block, Action action, boolean shift) {
            BlockData data = block.getState().getBlockData();

            return data instanceof TrapDoor || data instanceof Door || data instanceof Gate;
        }
    },

    //Levers and buttons
    SWITCH {
        @Override
        public boolean required(Block block, Action action, boolean shift) {
            return block.getState().getBlockData() instanceof Switch;
        }
    },

    //Chests etc
    CONTAINER {
        @Override
        public boolean required(Entity entity) {
            return entity instanceof Minecart && ((Minecart) entity).getDisplayBlock() instanceof Container;
        }
    },

    //Comparators, repeaters and daylight detectors
    REDSTONE {
        @Override
        public boolean required(Block block, Action action, boolean shift) {
            BlockData data = block.getState().getBlockData();

            return data instanceof Repeater || data instanceof Comparator || data instanceof DaylightDetector;
        }
    },

    //Boats and minecarts
    VEHICLES {

    },

    //Plants, composters and beehives
    FARMING {
        @Override
        public boolean required(Block block, Action action, boolean shift) {
            BlockData data = block.getState().getBlockData();

            return data instanceof Sapling || block.getType().equals(Material.COMPOSTER) || data instanceof Beehive;
        }
    },

    //Pressure plates, trip wires, and crop trampling
    PRESSURE_PLATE {
        @Override
        public boolean required(Block block, Action action, boolean shift) {
            return action.equals(Action.PHYSICAL);
        }
    },

    SLIMEFUN {
        @Override
        public boolean present() {
            return SlimefunIntegration.isEnabled();
        }
    };

    PlotPermission() {

    }

    public boolean present() {
        return true;
    }

    public boolean required(Block block, Action action, boolean shift) {
        return false;
    }

    public boolean required(Entity entity) {
        return false;
    }
}
