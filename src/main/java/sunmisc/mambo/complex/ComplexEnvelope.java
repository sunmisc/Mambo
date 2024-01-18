package sunmisc.mambo.complex;

import java.util.Objects;

public class ComplexEnvelope implements Complex {
    private final Number real, imaginary;

    public ComplexEnvelope(Number real, Number imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexEnvelope(Complex complex) {
        this(complex.real(), complex.imaginary());
    }

    @Override
    public Number real() {
        return real;
    }

    @Override
    public Number imaginary() {
        return imaginary;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ComplexEnvelope) obj;
        return Objects.equals(this.real, that.real) &&
                Objects.equals(this.imaginary, that.imaginary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }

    @Override
    public String toString() {
        return "ComplexEnvelope[" +
                "real=" + real + ", " +
                "imaginary=" + imaginary + ']';
    }

}
