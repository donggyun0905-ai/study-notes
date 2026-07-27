package ch06;

//같은 패키지 생략 가능
import java.awt.*;
import com.mysql.cj.jdbc.Driver;
import java.lang.*; //생략
/*cmd 실행
* 1. 컴파일:javac -d . PackageEx1.java -> ch06/PackageEx1.class생성
* --d 는 패키지 옵션
* -.컴파일 파일 저장 위치, 상대경로, 절대경로 가능 ex)javac -d C:\java
* 2. 실행 : java ch06.PackageEx1
* - 패키지 폴더가 보이는 곳에서 패키지명, 클래스명 실행함.
*
* */

/*패키지명: 도메인 거꾸로 선언
* String의 full name java.lang.String
* mysql에서 제공되는 String의 full name com.mysql.util.String
*
* */

public class PackageEx1 {

    Driver driver;
    Frame f;
    Static1 st;

    public static void main(String[] args) {
        System.out.println("ok~~~~");
    }
}
