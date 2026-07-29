package ch10;

public class ExceptionEx7 {

    int a = 100;
    public void divide(int b) throws Exception{
        if(b==0){
            //실제 예왹 객체를 생성되고 그 예외객체를 던진다
            throw new Exception("a를 0으로 나누면 안되요");
        }else{
            System.out.println(a+" / " + b + " = " + a/b);
        }
    }

    public static void main(String[] args) {
        ExceptionEx7 e7 = new ExceptionEx7();
        try {
            e7.divide(2);
            e7.divide(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

