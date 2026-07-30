package ch11;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateEx1 {
    public static void main(String[] args) {
        Date d = new Date();
        System.out.println(d);
        System.out.println(d.toLocaleString());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 mm월 dd일 hh시 mm분 ss초");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/mm/dd/");
        System.out.println(sdf1.format(d));
        System.out.println(sdf2.format(d));
    }
}
