package dsds;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RectanglePlotComponent implements LotComponent {
    public static void main(String[] args) {
        RectanglePlotComponent component = new RectanglePlotComponent(623,457,20,-10, null);
        RectanglePlotComponent component2 = new RectanglePlotComponent(10,1,20,-10, null);
    }

    private final int x1;
    private final int z1;
    private final int x2;
    private final int z2;

    private final World world;

    public RectanglePlotComponent(int x1, int z1, int x2, int z2, World world) {
        this.x1 = x1;
        this.z1 = z1;
        this.x2 = x2;
        this.z2 = z2;

        this.world = world;
    }

    public int getX1() {
        return x1;
    }

    public int getZ1() {
        return z1;
    }

    public int getX2() {
        return x2;
    }

    public int getZ2() {
        return z2;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public boolean isInArea(Location location) {
        if(!world.equals(location.getWorld())) {
            return false;
        }

        int xa = Math.max(x1, x2);
        int xi = Math.min(x1, x2);

        int za = Math.max(z1, z2);
        int zi = Math.min(z1, z2);

        return between(xi, xa, location.getBlockX()) && between(zi, za, location.getBlockZ());
    }

    private boolean between(int min, int max, int test) {
        return test <= max && test >= min;
    }

    @Override
    public List<Location> getPerimeter() {
        Set<Location> ret = new HashSet<>();

        int xa = Math.max(x1, x2);
        int xi = Math.min(x1, x2);

        int za = Math.max(z1, z2);
        int zi = Math.min(z1, z2);

        for(int xl = xi; xl <= xa; xl++) {
            ret.add(new Location(world, xl, 0, za));
            ret.add(new Location(world, xl, 0, zi));
        }

        for(int zl = zi; zl <= za; zl++) {
            ret.add(new Location(world, xa, 0, zl));
            ret.add(new Location(world, xi, 0, zl));
        }

        return new ArrayList<>(ret);
    }

    @Override
    public boolean isTouching(LotComponent other) {
        if(other instanceof RectanglePlotComponent) {
            RectanglePlotComponent rpc = ((RectanglePlotComponent) other);

            int x1a = Math.max(x1, x2);
            int x1i = Math.min(x1, x2);

            int z1a = Math.max(z1, z2);
            int z1i = Math.min(z1, z2);

            int x2a = Math.max(rpc.x1, rpc.x2);
            int x2i = Math.min(rpc.x1, rpc.x2);

            int z2a = Math.max(rpc.z1, rpc.z2);
            int z2i = Math.min(rpc.z1, rpc.z2);

            return (between(x1i-1, x1a+1, x2a) || between(x1i-1, x1a+1, x2i)) && (between(z1i, z1a, z2a) || between(z1i, z1a, z2i))
                    || (between(x1i, x1a, x2a) || between(x1i, x1a, x2i)) && (between(z1i-1, z1a+1, z2a) || between(z1i-1, z1a+1, z2i));

        }

        return LotComponent.super.isTouching(other);
    }

    @Override
    public boolean isIntersecting(LotComponent other) {
        if(other instanceof RectanglePlotComponent) {
            RectanglePlotComponent rpc = ((RectanglePlotComponent) other);

            int x1a = Math.max(x1, x2);
            int x1i = Math.min(x1, x2);

            int z1a = Math.max(z1, z2);
            int z1i = Math.min(z1, z2);

            int x2a = Math.max(rpc.x1, rpc.x2);
            int x2i = Math.min(rpc.x1, rpc.x2);

            int z2a = Math.max(rpc.z1, rpc.z2);
            int z2i = Math.min(rpc.z1, rpc.z2);

            return (between(x1i, x1a, x2a) || between(x1i, x1a, x2i)) && (between(z1i, z1a, z2a) || between(z1i, z1a, z2i));
        }

        return LotComponent.super.isTouching(other);
    }
}
