package sunmisc.mambo.numbers;

public class NumberEnvelope<T extends Number> extends Number {

    private final T origin;

    public NumberEnvelope(T origin) {
        this.origin = origin;
    }

    @Override
    public int intValue() {
        return origin.intValue();
    }

    @Override
    public long longValue() {
        return origin.longValue();
    }

    @Override
    public float floatValue() {
        return origin.floatValue();
    }

    @Override
    public double doubleValue() {
        return origin.doubleValue();
    }
}
