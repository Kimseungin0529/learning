package project.learning.java.datastructure.generic.ex2;

public class ContainerTest {
    public static void main(String[] args) {
        Container<String> strContainer = new Container<>();
        System.out.println("isEmpty: " + strContainer.isEmpty());

        strContainer.setItem("Hello, Generics!");
        System.out.println("Item: " + strContainer.getItem());
        System.out.println("isEmpty: " + strContainer.isEmpty());

        Container<Integer> intContainer = new Container<>();
        intContainer.setItem(42);
        System.out.println("Item: " + intContainer.getItem());
    }
}
