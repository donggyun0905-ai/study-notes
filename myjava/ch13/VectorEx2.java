package ch13;

import java.util.Vector;

public class VectorEx2 {
    public static void main(String[] args) {

        String city[] = {"노르웨이", "스위스", "뉴질랜드", "덴마크", "캐나다"};
        Vector vec1 = new Vector();
        Vector <String> vec2 = new Vector<String>();
        for (int i = 0; i < city.length; i++) {
            vec1.add(city[i]);
            vec2.add(city[i]);

            String str1 = (String)vec1.elementAt(i);
            String str2 = vec2.get(i);
            System.out.println(str1 + " : " + str2);
        }
        for (Object obj : vec1) {}
        for (String str:vec2) {}
        System.out.println("-------");
        vec2.remove(0);
        System.out.println(vec2.size());
        vec2.removeAllElements();
        System.out.println(vec2.size());
        System.out.println(vec2.capacity());
        //용량을 요소 크기만큼 줄이기
        vec2.trimToSize();
        System.out.println(vec2.size());
        System.out.println(vec2.capacity());
    }
}
