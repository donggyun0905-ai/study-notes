package ch02;
public class Ex2 {
    public static void main(String[] args) {
        //예약어로는 변수 선언 <- 문법에러 <- class안생김
        int a = 10;
        if(a>=10){
            System.out.println("실행되나요?");
            int d = 20;
            System.out.println(a+d);
        }
        //System.out.println(a+d);


    }
}
