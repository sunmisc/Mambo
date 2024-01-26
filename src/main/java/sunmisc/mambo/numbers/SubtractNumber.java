package sunmisc.mambo.numbers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public final class SubtractNumber extends NumberEnvelope<Number> {

    public SubtractNumber(Number... numbers) {
        this(List.of(numbers));
    }

    public SubtractNumber(Collection<? extends Number> numbers) {
        super(MappedFunction.SUBTRACT.apply(numbers.iterator()));
    }
    public SubtractNumber(int a, int b) {
        super(a - b);
    }
    public SubtractNumber(double a, double b) {
        super(a - b);
    }
    public SubtractNumber(BigDecimal a, BigDecimal b) {
        super(a.subtract(b));
    }
}
