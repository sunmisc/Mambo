package sunmisc.mambo.complex;

import sunmisc.mambo.numbers.AddNumber;
import sunmisc.mambo.numbers.DivideNumber;
import sunmisc.mambo.numbers.MultiplyNumber;
import sunmisc.mambo.numbers.SubtractNumber;

public final class DivisionComplex implements Complex {

    private final Complex a,b;

    public DivisionComplex(Complex a, Complex b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Number real() {
        final Number ar = a.real(), ai = a.imaginary();
        final Number br = b.real(), bi = b.imaginary();

        Number divisor = new AddNumber(
                new MultiplyNumber(br, br),
                new MultiplyNumber(bi, bi)
        );

        return new DivideNumber(
                new AddNumber(
                        new MultiplyNumber(ar, br),

                        new MultiplyNumber(ai, bi)
                ), divisor);
    }

    @Override
    public Number imaginary() {
        final Number ar = a.real(), ai = a.imaginary();
        final Number br = b.real(), bi = b.imaginary();

        Number divisor = new AddNumber(
                new MultiplyNumber(br, br),
                new MultiplyNumber(bi, bi)
        );
        return new DivideNumber(
                new SubtractNumber(
                        new MultiplyNumber(ar, br),

                        new MultiplyNumber(ai, bi)
                ), divisor);
    }
}
