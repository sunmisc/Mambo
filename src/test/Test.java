import sunmisc.mambo.FrameMaker;
import sunmisc.mambo.ImageIterator;
import sunmisc.mambo.MandelbrotMaker;

import javax.swing.*;
import java.awt.*;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public final class Test extends JPanel {

    private static final int WIDTH = 1000, HEIGHT = 1000;

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

        FrameMaker processor = new MandelbrotMaker(WIDTH, HEIGHT,
                () -> (double) SCALE.getOpaque(Test.this));

        itr = new ImageIterator(processor);
    }


    public void start() {
        while (true) {
            try {
                component.updateImage(itr.next());
                SCALE.setOpaque(this, scale * 0.97);
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

    private static final VarHandle SCALE;

    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            SCALE = l.findVarHandle(Test.class, "scale", double.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
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