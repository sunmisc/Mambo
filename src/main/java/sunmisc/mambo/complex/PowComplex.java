package sunmisc.mambo.complex;

import sunmisc.mambo.numbers.AddNumber;
import sunmisc.mambo.numbers.MultiplyNumber;
import sunmisc.mambo.numbers.SubtractNumber;

public final class PowComplex implements Complex {

    private final Complex origin;

    public PowComplex(Complex origin) {
        this.origin = origin;
    }


    @Override
    public Number real() {
        Number a = origin.real(), b = origin.imaginary();
        Number x = new MultiplyNumber(a,a),
               y = new MultiplyNumber(b,b);
        return new SubtractNumber(x,y);
    }

    @Override
    public Number imaginary() {
        Number a = origin.real(), b = origin.imaginary();
        Number p = new MultiplyNumber(a,b);
        return new AddNumber(p,p);
    }
}
