package ch04;

import java.util.Arrays;

public class LuckyLotto {
    public static void main(String[] args) {
        int arr[] = getLotto();
        for(int i =0;i<arr.length;i++){
            System.out.print(arr[i] + "\t");
        }
    }

    public static int[] getLotto(){
        int lotto[] = new int[6];
        boolean check[] = new boolean[45];
        for(int i =0;i<lotto.length;i++){
            lotto[i] = (int)(Math.random()*45) + 1;
            //중복 제거 로직
            if(check[lotto[i]-1] == false) {
                check[lotto[i]-1] = true;
            }
            else{
                i--;
            }
        }

        Arrays.sort(lotto);
        return lotto;
    }
    //1~45 사이값 중복 안되는 난수 오름차순 정렬
}
