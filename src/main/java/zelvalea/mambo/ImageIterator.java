package zelvalea.mambo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Iterator;

public class ImageIterator implements Iterator<Image> {
    private final BufferedImage buffer;
    private final FrameMaker maker;

    public ImageIterator(int width, int height, FrameMaker maker) {
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.maker = maker;
    }

    @Override public boolean hasNext() { return true; }

    @Override
    public Image next() {
        int[] data = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        maker.render(data);
        return buffer;
    }
}