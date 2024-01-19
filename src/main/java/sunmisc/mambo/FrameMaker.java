package sunmisc.mambo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;

public abstract class FrameMaker {

    static final int MIN_PARTITION = 1 << 12;

    static final int NCPU = ForkJoinPool.getCommonPoolParallelism();

    private final BufferedImage image;

    public FrameMaker(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public abstract int renderAt(int x, int y);


    public Image render() {
        BufferedImage im = image;
        int n = im.getWidth() * im.getHeight();
        int threshold = Math.max(n / (NCPU << 3), Math.min(n, MIN_PARTITION));
        new BulkTask(null, this,
                threshold,
                0, n
        ).invoke();
        return im;
    }

    private void renderChunk(int lo, int hi) {

        BufferedImage im = image;
        int w = im.getWidth(), h = im.getHeight();
        for (int i = lo; i < hi; ++i) {
            final int x = i / w, y = i % h;

            int pixel = renderAt(x, y);
            im.setRGB(x,y, pixel);
        }

    }

    private static final class BulkTask extends CountedCompleter<Void> {

        private final FrameMaker maker;
        private final int threshold;
        private final int hi, lo;

        BulkTask(BulkTask parent, FrameMaker maker,
                 int threshold,
                 int lo, int hi) {
            super(parent);
            this.maker = maker;
            this.threshold = threshold;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        public void compute() {
            final int size = hi - lo;
            final int th = threshold;

            if (size < th)
                maker.renderChunk(lo, hi);
            else {
                setPendingCount(2);
                int mid = (lo + hi) >>> 1;
                new BulkTask(this, maker, th, mid, hi).fork();
                new BulkTask(this, maker, th, lo, mid).fork();
            }
            propagateCompletion();
        }
    }

}