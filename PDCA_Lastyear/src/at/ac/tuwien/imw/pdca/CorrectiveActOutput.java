package at.ac.tuwien.imw.pdca;


/**
 * Generic act method result.
 *
 * @param <T>
 * @author ivanstojkovic
 */
public abstract class CorrectiveActOutput<T> {

    protected T value;

    public CorrectiveActOutput(T value) {
        super();
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
