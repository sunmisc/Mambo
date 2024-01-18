package sunmisc.mambo.complex;

public final class MultipleComplex implements Complex {

    private final Complex a,b;

    public MultipleComplex(Complex a, Complex b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Number real() {
        return  a.real().doubleValue()      * b.real().doubleValue() -
                a.imaginary().doubleValue() * b.imaginary().doubleValue();
    }

    @Override
    public Number imaginary() {
        return  a.real().doubleValue() * b.imaginary().doubleValue() +
                a.imaginary().doubleValue() * b.real().doubleValue();
    }
}
