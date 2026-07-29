package ch10;

public class ExceptionEx6 {
    public static void main(String[] args) {
        try {
            exec3();
            System.out.println("예외없이 실행");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //non-static 메소드는 static 메소드 안에 직접적인 호출은 불가. 반드시 객체를 생성
    public static void exec1() throws Exception{
        //예외가 일어날 수  있는 코드가 있다고 가정
        int c = 10/0;
    }

    public static void exec2() throws Exception{
        exec1();
    }
    public static void exec3() throws Exception{
        exec2();
    }
}
