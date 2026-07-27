package ch07;

class Car1/*2대 클래스*/{
    int velocity;
    void speedUp(){
        velocity++;
    }
    void speedDown(){
        velocity--;
        if(velocity<0)
            velocity = 0;
    }

    void stop(){
        velocity = 0;
    }
}

class Bus1 extends Car1{
    @Override
    void speedUp(){
        super.speedUp();
        if(velocity>100)
            velocity = 100;
    }
}

class Taxi1 extends Car1{
    @Override
    void speedUp(){
        velocity += 5;
    }

    @Deprecated
    void prn(){
        System.out.println("velocity: "+ velocity);
    }
}

public class InheritanceEx1 {
    public static void main(String[] args) {
        Integer i1 = new Integer(22);
        Taxi1 t1 = new Taxi1();
        t1.speedUp();
        t1.prn();
    }
}
