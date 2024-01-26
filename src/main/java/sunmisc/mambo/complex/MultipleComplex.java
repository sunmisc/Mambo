package sunmisc.mambo.complex;

import sunmisc.mambo.numbers.AddNumber;
import sunmisc.mambo.numbers.MultiplyNumber;
import sunmisc.mambo.numbers.SubtractNumber;

public final class MultipleComplex implements Complex {

    private final Complex a,b;

    public MultipleComplex(Complex a, Complex b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Number real() {
        return new SubtractNumber(
                new MultiplyNumber(a.real(), b.real()),
                new MultiplyNumber(a.imaginary(), b.imaginary())
        );
    }

    @Override
    public Number imaginary() {
        return new AddNumber(
                new MultiplyNumber(a.real(), b.imaginary()),
                new MultiplyNumber(a.imaginary(), b.real())
        );
    }
}
