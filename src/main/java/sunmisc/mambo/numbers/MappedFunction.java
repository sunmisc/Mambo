package sunmisc.mambo.numbers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public interface MappedFunction<T extends Number>
        extends Function<Iterator<? extends Number>,T> {

    MappedFunction<Number> ADD = iterator -> {
        BigDecimal start = BigDecimal.ZERO;
        double sum = 0;
        while (iterator.hasNext()) {
            Number number = iterator.next();
            if (number instanceof BigDecimal r)
                start = start.add(r);
            else
                sum += number.doubleValue();
        }
        return start.equals(BigDecimal.ZERO)
                ? sum
                : start.add(BigDecimal.valueOf(sum));
    };

    MappedFunction<Number> SUBTRACT = iterator -> {
        double prim = 0;
        BigDecimal big = null;
        if (iterator.hasNext()) {
            Number n = iterator.next();
            if (n instanceof BigDecimal r)
                big = r;
            else
                prim = n.doubleValue();
        } else throw new NoSuchElementException();

        Number b = ADD.apply(iterator);
        if (b instanceof BigDecimal r)
            return r.subtract(big == null ? BigDecimal.valueOf(prim) : big).negate();
        else
            return big == null
                    ? -(b.doubleValue() - prim)
                    : BigDecimal.valueOf(b.doubleValue()).subtract(big).negate();
    };

    MappedFunction<Number> MULTIPLY = iterator -> {
        BigDecimal start = BigDecimal.ONE;
        double sum = 1;

        while (iterator.hasNext()) {
            Number number = iterator.next();
            if (number instanceof BigDecimal r)
                start = start.multiply(r);
            else
                sum *= number.doubleValue();
        }
        return start.equals(BigDecimal.ONE)
                ? sum
                : start.multiply(BigDecimal.valueOf(sum));
    };

    MappedFunction<Number> DIVIDE = iterator -> {
        double prim = 0;
        BigDecimal big = null;
        if (iterator.hasNext()) {
            Number n = iterator.next();
            if (n instanceof BigDecimal r)
                big = r;
            else
                prim = n.doubleValue();
        } else throw new NoSuchElementException();

        boolean biggest = big != null;

        while (iterator.hasNext()) {
            Number number = iterator.next();
            if (biggest) {
                BigDecimal r = number instanceof BigDecimal t
                        ? t : BigDecimal.valueOf(number.doubleValue());
                big = big.divide(r, MathContext.DECIMAL128);
            } else if (number instanceof BigDecimal r) {
                big = BigDecimal.valueOf(prim).divide(r, MathContext.DECIMAL128);
                biggest = true;
            } else {
                prim /= number.doubleValue();
            }
        }
        return biggest ? big : prim;
    };
}
