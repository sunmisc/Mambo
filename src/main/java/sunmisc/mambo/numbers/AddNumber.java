package sunmisc.mambo.numbers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public final class AddNumber extends NumberEnvelope<Number> {

    public AddNumber(Number... numbers) {
        this(List.of(numbers));
    }

    public AddNumber(Collection<? extends Number> numbers) {
        super(MappedFunction.ADD.apply(numbers.iterator()));
    }
    public AddNumber(int a, int b) {
        super(a + b);
    }
    public AddNumber(double a, double b) {
        super(a + b);
    }

    public AddNumber(BigDecimal a, BigDecimal b) {
        super(a.add(b));
    }
}
