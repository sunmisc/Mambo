package sunmisc.mambo.numbers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.List;

public final class DivideNumber extends NumberEnvelope<Number> {

    public DivideNumber(Number... numbers) {
        this(List.of(numbers));
    }

    public DivideNumber(Collection<? extends Number> numbers) {
        super(MappedFunction.DIVIDE.apply(numbers.iterator()));
    }
    public DivideNumber(int a, int b) {
        super(a / b);
    }
    public DivideNumber(double a, double b) {
        super(a / b);
    }

    public DivideNumber(BigDecimal a, BigDecimal b) {
        super(a.divide(b, MathContext.DECIMAL128));
    }
}
