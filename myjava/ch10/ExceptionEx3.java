package ch10;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionEx3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            sc = new Scanner(System.in);
            int a,b;
            System.out.print("첫번째 숫자: ");
            a = sc.nextInt();
            System.out.print("두번째 숫자: ");
            b = sc.nextInt();
            System.out.println(a + "/" + b + " = " + (a/b));
            //다중 catch는 상위 Exception 클래스가 위로 올라오면 절대 안됨. 모든 예외는 Exception 캐치(catch)
            //하위 클래스 부터 catch 되어야 함.
        }catch (InputMismatchException e){
            System.out.println("숫자만 입력을 해야 합니다.");
        } catch (ArithmeticException e){
            System.out.println("0으로 나누면 안되요.");
        } catch(Exception e){
            //예외가 일어나는 모든 경로까지 출력(디버깅 필수)
            System.out.println(e.getMessage());
        }finally {
            //항상 사용된 스트림은 사용 후에 close 해야함 .java.io 학습
            sc.close();
        }
    }
}
