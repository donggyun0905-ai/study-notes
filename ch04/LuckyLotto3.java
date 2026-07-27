package ch04;

import java.util.Arrays;
import java.util.TreeSet;

public class LuckyLotto3 {
    public static void main(String[] args) {
        int arr[] = getLotto();
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }

    }

    // 1~45 사이의 중복되지 않는 6개의 난수를 오름차순 리턴
    public static int[] getLotto(){
        int lotto[] = new int[6];
        for (int i = 0; i < lotto.length; i++) {
            int num = (int)(Math.random() * 45) + 1;
            if (isDeplication(lotto, num)) {
                i--; // 중복일때
            }else{
                lotto[i] = num; // 중복이 아닐때
            }
        }
        Arrays.sort(lotto);
        return lotto;
    }

    // "12 45 3 35".containes("2"): 중복이면 true, 아니면 false
    public static boolean isDeplication(int arr[], int num) {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            str += arr[i] + " "; // int형 배열을 문자열으로 변환. 구분자는 공백문자
        } // "12 45 3 34"
        // String.valueOf -> 정수값을 문자열로 변환
        return str.contains(String.valueOf(num));
    }
}
