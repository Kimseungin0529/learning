package project.learning.java.generic.ex2;

public class Pair<T, S> {
    private T first;
    private S second;

    public Pair() {
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
