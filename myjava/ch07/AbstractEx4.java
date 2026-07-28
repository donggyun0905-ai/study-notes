package ch07;

//추상클래스 : 도형
abstract class Shape{
    int x,y;
    void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    abstract void draw();
}

class Triangle extends Shape{
    @Override
    void draw() {
        System.out.println("삼각형 그리기");
    }
}

class Retangle extends Shape{
    @Override
    void draw() {
        System.out.println("사각형 그리기");
    }
}

class Circle extends Shape{
    @Override
    void draw() {
        System.out.println("원 그리기");
    }
}

public class AbstractEx4 {
    public static void main(String[] args) {
        Shape s = new Triangle();
        s.draw();//동적바인딩 : 자바의 특징 -> 다형성
        s = new Retangle();
        s.draw();
        s = new Circle();
        s.draw();
    }
}
