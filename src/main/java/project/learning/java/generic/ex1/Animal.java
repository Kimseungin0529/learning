package project.learning.java.generic.ex1;

public class Animal {
    private String name;

    public Animal() {
        System.out.println("Animal 기본 생성자 호출");
    }

    public Animal(String name) {
        this.name = name;
        System.out.println("Animal 생성자 호출");
    }

    public void sound() {
        System.out.println("동물 울음 소리");
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                '}';
    }
}
