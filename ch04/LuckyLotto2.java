package ch04;

import java.util.TreeSet;

public class LuckyLotto2 {
    public static void main(String[] args) {
        Object arr[] = getLottos();
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }

    public static Object[] getLottos() {
        //TreeSet(자료구조): 중복과 정렬이 자동으로 만들어지는 클래스
        TreeSet<Integer> ts = new TreeSet<Integer>();
        for (int i = 0; ts.size() < 6; i++) { // ts사이즈가 6 될때까지
            ts.add((int)(Math.random()*45 + 1));
        }
        Object obj[] = ts.toArray();
        return obj;
    }
}
