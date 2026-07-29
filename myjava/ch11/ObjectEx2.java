package ch11;

class Point2{
    int x,y;

    public Point2(int x, int y){
        this.x =x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}

public class ObjectEx2 {
    public static void main(String[] args) {
        Object obj;
        obj = new Point2(10,20);
        //Object 클래스의 toString 메소드가 아닌 서브클래스의 toString() 메소드 호출
        System.out.println(obj.toString());
    }
}
