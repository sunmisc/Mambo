package sunmisc.mambo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class ImageIterator implements Iterator<Image> {
    private final BufferedImage buffer;
    private final FrameMaker maker;

    public ImageIterator(int width, int height, FrameMaker maker) {
        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.maker = maker;
    }

    @Override public boolean hasNext() { return true; }

    @Override
    public Image next() {
        int[] data = maker.render();
        int w = buffer.getWidth(),
            h = buffer.getHeight();
        buffer.setRGB(0, 0, w, h, data, 0, w);
        return buffer;
    }
}