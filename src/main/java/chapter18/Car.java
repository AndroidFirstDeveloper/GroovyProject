package chapter18;

//编译java文件使用指定的jdk，并将生成的class文件放到指定目录
//javac -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\java\Car.java
public class Car {
    private int miles;

    public int getMiles() {
        return miles;
    }

    public void drive(int dist) {
        miles += dist;
    }
}
