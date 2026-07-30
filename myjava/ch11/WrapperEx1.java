package ch11;

import common.util.StringUtil;

import java.util.Vector;

public class WrapperEx1 {
    public static void main(String[] args) {
        //Wrapper 클리스 : 자바 기능형 데이터 8개를 객체화 시킨 클래스의 집합개념
        //byte, short, long, float, double, boolean,int(약어), char(약어)
        int a = 10;
        Integer it =Integer.valueOf(a);//int 형에서 Integer형 변환
        Integer it2 = a; //Auto Boxing
        int b = it2; //Auto Unboxing

        Vector vec = new Vector();//객체(Object)를 저장하는 클래스
        vec.add(new String("하하"));
        vec.add(new Object());
        vec.add(a);//AutoBoxing <- Integer 타입으로 변환 되어서 저장
        String str = (String)vec.get(0);
        System.out.println(str);
        int c = (Integer) vec.get(2);
        System.out.println(c);

        Integer it3 = new Integer(a);
        Integer it4 = new Integer("22");
        Integer it5 = Integer.valueOf(a);
        Integer it6 = Integer.parseInt("23");

        //그럼 왜 int 을 Integer 형을 변환 :
        int d = Integer.parseInt("24");

        System.out.println("2진수: " + Integer.toBinaryString(d));
        System.out.println("8진수: " + Integer.toOctalString(d));
        System.out.println("16진수: " + Integer.toHexString(d));

        System.out.println(StringUtil.addComma(Integer.MAX_VALUE));

    }
}










