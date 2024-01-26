package sunmisc.mambo.complex;

import sunmisc.mambo.numbers.AddNumber;

public final class AddComplex implements Complex {

    private final Complex a, b;

    public AddComplex(Complex a, Complex b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Number real() {
        return new AddNumber(a.real(), b.real());
    }

    @Override
    public Number imaginary() {
        return new AddNumber(a.imaginary(), b.imaginary());
    }
}
