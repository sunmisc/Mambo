import sunmisc.mambo.FrameMaker;
import sunmisc.mambo.ImageIterator;
import sunmisc.mambo.MandelbrotMaker;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public final class Test extends JPanel {

    private static final int WIDTH = 700, HEIGHT = 700;

    private final UpdatableImageComponent component
            = new UpdatableImageComponent();

    private final Iterator<Image> itr;
    private double scale = 0.05;

    private Test() {
        super(new BorderLayout());
        setOpaque(false);

        final Dimension dimension = new Dimension(WIDTH, HEIGHT);

        setPreferredSize(dimension);

        add(component, BorderLayout.CENTER);

        FrameMaker processor = new MandelbrotMaker(WIDTH, HEIGHT, () -> scale);

        itr = new ImageIterator(processor);
    }


    public void start() {
        while (true) {
            try {
                component.updateImage(itr.next());
                scale *= 0.97;
            } catch (InterruptedException | InvocationTargetException e) {
                Thread.interrupted();
            }
        }
    }

    public static void main(String[] args) {

        Test test = new Test();

        JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(test);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        test.start();
    }


    private static class UpdatableImageComponent extends JComponent {
        private Image image;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image im = image;
            if (im == null)
                return;
            g.drawImage(im, 0, 0,
                    im.getWidth(this),
                    im.getHeight(this), this
            );
        }

        public void updateImage(Image im)
                throws InterruptedException, InvocationTargetException {
            image = im;
            repaint();
        }
    }
}