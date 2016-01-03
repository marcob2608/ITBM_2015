package at.ac.tuwien.imw.pdca;


/**
 * Generic deviation value.
 * In most cases it will be a value (decimal number) that means something to the system.
 *
 * @param <T>
 * @author ivanstojkovic
 */
public abstract class Deviation<T> {

    protected T value;
    private Long id;

    public Deviation(T value) {
        super();
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }

}
