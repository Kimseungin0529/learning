package project.learning.java.자바_자료구조.generic.ex1;

public class BoxMain {

    public static void main(String[] args) {

        Dog dog = new Dog("뽀삐");
        dog.sound();

        Box<Dog> dogBox = new Box<>(dog);
        Dog findDog = dogBox.getValue();
        findDog.sound();

        System.out.println(dog == findDog);
    }
}
