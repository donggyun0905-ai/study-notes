package ch13;

import java.util.Vector;

//다양한 타입의 data를 저장
class Box1{
    private Object data;
    public void set(Object data) {this.data = data;}
    public Object get() {return data;}
}

class  Box2<K>{ /*가상의 data 타입*/
    private K data;
    public void set(K data) {this.data = data;}
    public K get() {return data;}
}

class  Box3<T,Z>{ /*가상의 data 타입*/
    private T key;
    private Z value;

}

public class GenericEx1 {
    public static void main(String[] args) {
        Vector<String> vec = new Vector<String>();
        Box1 b1 = new Box1();
        b1.set(Integer.valueOf(22));
        Integer t1 = (Integer) b1.get();

        Box2<String> b2 = new Box2<String>();
        b2.set("손흥민");
        String str2 = b2.get();
    }
}
