package sunmisc.mambo.complex;

public class DivisionComplex implements Complex {

    private final Complex a,b;

    public DivisionComplex(Complex a, Complex b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Number real() {
        final double
                ar = a.real().doubleValue(),
                ai = a.imaginary().doubleValue();
        final double
                br = b.real().doubleValue(),
                bi = b.imaginary().doubleValue();

        final double divisor = br * br + bi * bi;

        return (ar * br + ai * bi) / divisor;
    }

    @Override
    public Number imaginary() {
        final double
                ar = a.real().doubleValue(),
                ai = a.imaginary().doubleValue();
        final double
                br = b.real().doubleValue(),
                bi = b.imaginary().doubleValue();

        final double divisor = br * br + bi * bi;

        return (ai * br - ar * bi) / divisor;
    }
}
