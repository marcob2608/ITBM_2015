package at.ac.tuwien.imw.pdca;


/**
 * This is the result of performance measure
 *
 * @param <T>
 * @author ivanstojkovic
 */
public abstract class MeasuredPerformanceValue<T> {

    protected T value;

    public MeasuredPerformanceValue(T value) {
        super();
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
