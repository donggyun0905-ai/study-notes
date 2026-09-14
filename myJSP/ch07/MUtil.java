package ch07;

import java.util.Random;
import jakarta.servlet.http.HttpServletRequest;

public class MUtil {
    public static String randomColor(){
        Random r = new Random();
        // %02x로 두 자리씩 고정해야 합니다. 안 그러면 0~15 사이 값일 때
        // 한 글자만 나와서 "#5a3" 처럼 깨진 색상 코드가 나올 수 있습니다.
        String rgb = String.format("%02x", r.nextInt(256));
        rgb += String.format("%02x", r.nextInt(256));
        rgb += String.format("%02x", r.nextInt(256));
        return "#" + rgb;
    }
    //정수로 넘긴 값을 정수로 변환 기능
    public static int parseInt(HttpServletRequest request,String name){
        return Integer.parseInt(request.getParameter(name));
    }
}
