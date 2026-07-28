package ch07;

//싱글톤 패턴 : 주어진 클래스 통해 오직 하나의 객체만 만들 수 있도록 만든 기법

class Singleton1{

    private static Singleton1 instance = null;

    //외부에서 객체를 생성 불가능
    private Singleton1(){}
    //필드 instance 객체가 null이면 객체를 생성하고 null이 아니면 Singleton1 객체를 리턴

    public static Singleton1 getInstance(){
        if(instance==null)
            instance = new Singleton1();//객체 생성
        return  instance;
    }
}

public class SingletonEx1 {
    public static void main(String[] args) {
        //Singleton1 st = new SingletonEx1();
        Singleton1 st1 = Singleton1.getInstance();
        Singleton1 st2 = Singleton1.getInstance();
        System.out.println(st1);
        System.out.println(st2);
    }
}
