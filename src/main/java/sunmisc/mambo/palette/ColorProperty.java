package sunmisc.mambo.palette;

import java.util.function.IntUnaryOperator;

public class ColorProperty implements IntUnaryOperator {
    private final double start;
    private final int step;

    public ColorProperty(double start, int step) {
        this.start = start;
        this.step = step;
    }

    @Override
    public int applyAsInt(int i) {

        int totalRange = 255;

        int range = totalRange / step;

        double cos = Math.cos(i * Math.TAU / range + start);

        return (int) ((cos * totalRange) + totalRange) / 2;
    }

}
