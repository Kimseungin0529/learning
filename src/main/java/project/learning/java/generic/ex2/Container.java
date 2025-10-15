package project.learning.java.generic.ex2;

public class Container<T> {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public T getItem() {
        return item;
    }
}
