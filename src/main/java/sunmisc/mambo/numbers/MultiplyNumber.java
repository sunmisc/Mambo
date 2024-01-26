package sunmisc.mambo.numbers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public final class MultiplyNumber extends NumberEnvelope<Number> {

    public MultiplyNumber(Number... numbers) {
        this(List.of(numbers));
    }

    public MultiplyNumber(Collection<? extends Number> numbers) {
        super(MappedFunction.MULTIPLY.apply(numbers.iterator()));
    }

    public MultiplyNumber(int a, int b) {
        super(a * b);
    }
    public MultiplyNumber(double a, double b) {
        super(a * b);
    }

    public MultiplyNumber(BigDecimal a, BigDecimal b) {
        super(a.multiply(b));
    }
}