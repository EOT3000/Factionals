package dsds;

import org.bukkit.World;

public class TestRectangle {
    public static void main(String[] args) {
        TestRectangle component = new TestRectangle(623,457,620,455);
        TestRectangle component2 = new TestRectangle(620,453,618,453);

        System.out.println(component.isTouching(component2));
        System.out.println(component.isIntersecting(component2));
    }

    private final int x1;
    private final int z1;
    private final int x2;
    private final int z2;

    public TestRectangle(int x1, int z1, int x2, int z2) {
        this.x1 = x1;
        this.z1 = z1;
        this.x2 = x2;
        this.z2 = z2;
    }

    public boolean isTouching(TestRectangle other) {
        if(other instanceof TestRectangle) {
            TestRectangle rpc = ((TestRectangle) other);

            int x1a = Math.max(x1, x2);
            int x1i = Math.min(x1, x2);

            int z1a = Math.max(z1, z2);
            int z1i = Math.min(z1, z2);

            int x2a = Math.max(rpc.x1, rpc.x2);
            int x2i = Math.min(rpc.x1, rpc.x2);

            int z2a = Math.max(rpc.z1, rpc.z2);
            int z2i = Math.min(rpc.z1, rpc.z2);

            return ((between(x1i - 1, x1a + 1, x2a) || between(x1i - 1, x1a + 1, x2i)) && (between(z1i, z1a, z2a) || between(z1i, z1a, z2i)))
                    || ((between(x1i, x1a, x2a) || between(x1i, x1a, x2i)) && (between(z1i - 1, z1a + 1, z2a) || between(z1i - 1, z1a + 1, z2i)));

        }

        throw new RuntimeException();
    }

    private boolean between(int min, int max, int test) {
        return test <= max && test >= min;
    }

    public boolean isIntersecting(TestRectangle other) {
        if(other instanceof TestRectangle) {
            TestRectangle rpc = ((TestRectangle) other);

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

        throw new RuntimeException();
    }

}
