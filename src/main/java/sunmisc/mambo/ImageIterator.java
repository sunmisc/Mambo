package sunmisc.mambo;

import java.awt.*;
import java.util.Iterator;

public class ImageIterator implements Iterator<Image> {
    private final FrameMaker maker;

    public ImageIterator(FrameMaker maker) {
        this.maker = maker;
    }

    @Override public boolean hasNext() { return true; }

    @Override
    public Image next() {
        return maker.render();
    }
}