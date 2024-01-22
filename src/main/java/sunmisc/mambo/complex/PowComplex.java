package sunmisc.mambo.complex;

public final class PowComplex implements Complex {

    private final Complex origin;

    public PowComplex(Complex origin) {
        this.origin = origin;
    }


    @Override
    public Number real() {
        double x = origin.real().doubleValue();
        double y = origin.imaginary().doubleValue();

        return (x * x) - (y * y);
    }

    @Override
    public Number imaginary() {
        double p =
                origin.real().doubleValue() *
                origin.imaginary().doubleValue();
        return p + p;
    }
}
