package sunmisc.mambo.complex;

import sunmisc.mambo.numbers.AddNumber;
import sunmisc.mambo.numbers.MultiplyNumber;

public final class NormComplex extends Number {

    private final Complex origin;

    public NormComplex(Complex origin) {
        this.origin = origin;
    }

    @Override
    public int intValue() {
        Number a = origin.real(), b = origin.imaginary();
        return new AddNumber(
                new MultiplyNumber(a,a),
                new MultiplyNumber(b,b)
        ).intValue();
    }

    @Override
    public long longValue() {
        Number a = origin.real(), b = origin.imaginary();
        return new AddNumber(
                new MultiplyNumber(a,a),
                new MultiplyNumber(b,b)
        ).longValue();
    }

    @Override
    public float floatValue() {
        Number a = origin.real(), b = origin.imaginary();
        return new AddNumber(
                new MultiplyNumber(a,a),
                new MultiplyNumber(b,b)
        ).floatValue();
    }

    @Override
    public double doubleValue() {
        Number a = origin.real(), b = origin.imaginary();
        return new AddNumber(
                new MultiplyNumber(a,a),
                new MultiplyNumber(b,b)
        ).doubleValue();
    }
}
