package sunmisc.mambo.complex;

public final class AddComplex implements Complex {

    private final Complex a, b;

    public AddComplex(Complex a, Complex b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Number real() {
        return a.real().doubleValue() + b.real().doubleValue();
    }

    @Override
    public Number imaginary() {
        return a.imaginary().doubleValue() + b.imaginary().doubleValue();
    }
}
