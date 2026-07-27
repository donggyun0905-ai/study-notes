package ch06;

class This1{
    This1(This2 t2){

    }
}

class This2{
    This2(){
        //This1 의 생성자는 매개변수 This2의 객체
        //this(객체자신):그래픽에서 많이 사용 예정
        //this는 자신의 객체를 레퍼런스 객체주소값
        This1 t = new This1(null);
    }
}

public class ThisEx1 {
    public static void main(String[] args) {
        int a = 10;
        int b = a;
        String t3 = new String();
        String t4 = t3;
        System.out.println(t3);
        System.out.println(t4);
    }
}
