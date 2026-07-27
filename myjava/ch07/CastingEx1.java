package ch07;

public class CastingEx1 {
    public static void main(String[] args) {

        System.out.println(Short.MAX_VALUE);//32767
        int a = 32768;//short에 범위를 벗어난 값
        long l = a;//묵시적 형변환: 작은값(int)이 큰값(long)으로 변환
        short s = (short)a;//명시적 형변환 : 데이터 손실 일어남
        System.out.println(l);
        System.out.println(s);

    }
}
