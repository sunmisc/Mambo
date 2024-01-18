package sunmisc.mambo.complex;

public final class ModuleSqrComplex extends Number {

    private final Complex origin;

    public ModuleSqrComplex(Complex origin) {
        this.origin = origin;
    }

    @Override
    public int intValue() {
        return  origin.real().intValue() * origin.real().intValue() +
                origin.imaginary().intValue() * origin.imaginary().intValue();
    }

    @Override
    public long longValue() {
        return  origin.real().longValue() * origin.real().longValue() +
                origin.imaginary().longValue() * origin.imaginary().longValue();
    }

    @Override
    public float floatValue() {
        return  origin.real().floatValue() * origin.real().floatValue() +
                origin.imaginary().floatValue() * origin.imaginary().floatValue();
    }

    @Override
    public double doubleValue() {
        return  origin.real().doubleValue() * origin.real().doubleValue() +
                origin.imaginary().doubleValue() * origin.imaginary().doubleValue();
    }
}
