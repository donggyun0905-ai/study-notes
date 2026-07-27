package ch07;

class Animal{
    String name;
    void move(){
         System.out.println("동물아 움직여라~");
    }
}

class Bird extends Animal{
    @Override
    void move(){
        name = "새";
        System.out.println(name + "날아라~");
    }
}
class Fish extends Animal{
    @Override
    void move(){
        name = "물고기";
        System.out.println(name + "헤엄쳐라~");
    }
}
class Cheetah extends Animal{
    @Override
    void move(){
        name = "치타";
        System.out.println(name + "달려라~");
    }
}

public class CastingEx3 {
    public static void main(String[] args) {
        Animal ani[] = new Animal[3];
        ani[0] = new Bird();
        ani[1] = new Fish();
        ani[2] = new Cheetah();

        //공통적으로
        for(int i =0;i<ani.length;i++){
            //동작바인딩:Override 된 메소드는 하위클래스 메소드가 호출 .JVM결정
            //상위 클래스 변수로 하위클래스 메소드를 핸들링 할  수 잇는 기법
            ani[i].move();
        }

        Fish f = new Fish();
        Object obj = f;
        System.out.println(f);
        System.out.println(obj);
        f.move();
        //obj.move(); //하위클래스의 메소드 호출은 불가
        System.out.println(obj.hashCode());

        Animal ani2 = new Animal();
        //Bird b = (Bird)ani2;//강제로 casting 경우 컴파일 시점은 에러가 발생되지 않지만 실행 시점에 에러발생.
    }
}
