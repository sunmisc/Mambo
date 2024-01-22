package sunmisc.mambo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public abstract class FrameMaker {

    private final int width, height;


    public FrameMaker(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract Color renderAt(int x, int y);


    public Image render() {
        final int w = width, h = height;
        BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        IntStream.range(0, w * h)
                .parallel()
                .forEach(i -> {
                    final int x = i / w, y = i % h;

                    Color pixel = renderAt(x, y);
                    im.setRGB(x,y, pixel.getRGB());
                });
        return im;
    }

}