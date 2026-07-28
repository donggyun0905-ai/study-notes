package ch08;

abstract class Tv{
    String color;
    boolean power;
    int ch;

    void onOff(){
        power = !power;
    }

    void upCh(){
        ch++;
        if(ch<1000)
            ch = 0;
    }

    void downCh(){
        ch--;
        if(ch<0)
            ch = 999;
    }
    abstract void setModel();
}

class SMTv extends Tv implements RemoteControl{
    @Override
    public void turnOff() {

    }

    @Override
    public void turnOn() {

    }

    @Override
    void setModel(){}
}

class LGTv extends Tv implements RemoteControl{
    @Override
    public void turnOff() {

    }

    @Override
    public void turnOn() {

    }

    @Override
    void setModel(){}
}

interface RemoteControl{
    void turnOn();
    void turnOff();
}

public class InterfaceEx5 {
    public static void main(String[] args) {

    }
}
